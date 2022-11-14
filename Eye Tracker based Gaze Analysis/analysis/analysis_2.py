import os

# custom
from gazeanalyser.edfreader import read_edf
from gazeanalyser._visual_functions import generate_fixations_map, draw_heatmap, generate_scanpath_map, generate__raw_map

# external
import numpy


# # # # #
# CONSTANTS

# PARTICIPANTS
PPS = ['demo','demo2']

# DIRECTORIES
# paths
DIR = os.path.dirname(__file__)
IMGDIR = os.path.join(DIR, 'imgs')
DATADIR = os.path.join(DIR, 'data')
PLOTDIR = os.path.join(DIR, 'visual_analytics_results')
OUTPUTFILENAME = os.path.join(DIR, "output.txt")
# check if the image directory exists
if not os.path.isdir(IMGDIR):
	raise Exception("ERROR: no image directory found; path '%s' does not exist!" % IMGDIR)
# check if the data directory exists
if not os.path.isdir(DATADIR):
	raise Exception("ERROR: no data directory found; path '%s' does not exist!" % DATADIR)
# check if output directorie exist; if not, create it
if not os.path.isdir(PLOTDIR):
	os.mkdir(PLOTDIR)

# DATA FILES
SEP = '\t' # value separator
EDFSTART = "TRIALSTART" # EDF file trial start message
EDFSTOP = "TRIALEND" # EDF file trial end message
TRIALORDER = [EDFSTART, 'image online','image offline', EDFSTOP]
INVALCODE = 0.0 # value coding invalid data

# EXPERIMENT SPECS
DISPSIZE = (1024,768) # (px,px)
SCREENSIZE = (39.9,29.9) # (cm,cm)
SCREENDIST = 61.0 # cm
PXPERCM = numpy.mean([DISPSIZE[0]/SCREENSIZE[0],DISPSIZE[1]/SCREENSIZE[1]]) # px/cm


# # # # #
# READ FILES

# loop through all participants
for ppname in PPS:
	
	print("starting data analysis for participant '%s'" % (ppname))

	# BEHAVIOUR
	print("loading behavioural data")
	
	# path
	fp = os.path.join(DATADIR, '%s.txt' % ppname)
	
	# open the file
	fl = open(fp, 'r')
	
	# read the file content
	data = fl.readlines()
	
	# close the file
	fl.close()
	
	# separate header from rest of file
	header = data.pop(0)
	header = header.replace('\n','').replace('\r','').replace('"','').split(SEP)
	
	# process file contents
	for i in range(len(data)):
		data[i] = data[i].replace('\n','').replace('\r','').replace('"','').split(SEP)
	
	# GAZE DATA
	print("loading gaze data")
	
	# path
	fp = os.path.join(DATADIR, '%s.asc' % ppname)
	
	# check if the path exist
	if not os.path.isfile(fp):
		# if not, check if the EDF exists
		edfp = os.path.join(DATADIR, '%s.edf' % ppname)
		if os.path.isfile(edfp):
			# if it does, convert if usinf edf2asc.exe
			os.system("edf2asc %s" % edfp)
			# load ASCII
			fp = os.path.join(DATADIR, '%s.asc' % ppname)
		# if it does not exist, raise an exception
		else:
			raise Exception("No eye data file (neither ASC, nor EDF) file found for participant '%s' (tried paths:\nASC: %s\nEDF: %s" % (ppname, fp, edfp))
	
	# read the file

	edfdata = read_edf(fp, EDFSTART, stop=EDFSTOP, missing=INVALCODE, debug=False)
	
	# NEW OUTPUT DIRECTORIES
	# create a new output directory for the current participant
	pplotdir = os.path.join(PLOTDIR, ppname)
	# check if the directory already exists
	if not os.path.isdir(pplotdir):
		# create it if it doesn't yet exist
		os.mkdir(pplotdir)


	# # # # #
	# PLOTS
	
	print("plotting gaze data")

	# loop through trials
	for trialnr in range(len(data)):
		
		# load image name, saccades, and fixations
		imgname = data[trialnr][header.index("image")]
		saccades = edfdata[trialnr]['events']['Esac'] # [starttime, endtime, duration, startx, starty, endx, endy]
		fixations = edfdata[trialnr]['events']['Efix'] # [starttime, endtime, duration, endx, endy]
		
		# paths
		imagefile = os.path.join(IMGDIR,imgname)
		rawplotfile = os.path.join(pplotdir, "raw_data_%s_%d" % (ppname,trialnr))
		scatterfile = os.path.join(pplotdir, "fixations_%s_%d" % (ppname,trialnr))
		scanpathfile =  os.path.join(pplotdir, "scanpath_%s_%d" % (ppname,trialnr))
		heatmapfile = os.path.join(pplotdir, "heatmap_%s_%d" % (ppname,trialnr))
		
		# raw data points
		generate__raw_map(edfdata[trialnr]['x'], edfdata[trialnr]['y'], DISPSIZE, imagefile=imagefile, savefilename=rawplotfile)

		# fixations
		generate_fixations_map(fixations, DISPSIZE, imagefile=imagefile, durationsize=True, durationcolour=False, alpha=0.5, savefilename=scatterfile)
		
		# scanpath
		generate_scanpath_map(fixations, saccades, DISPSIZE, imagefile=imagefile, alpha=0.5, savefilename=scanpathfile)

		# heatmap		
		draw_heatmap(fixations, DISPSIZE, imagefile=imagefile, durationweight=True, alpha=0.5, savefilename=heatmapfile)

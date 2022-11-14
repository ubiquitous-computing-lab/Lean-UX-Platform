
import os

# custom
from gazeanalyser.edfreader import read_edf
from gazeanalyser.opengazereader import  read_opengaze
from gazeanalyser._visual_functions import generate_fixations_map, draw_heatmap, generate_scanpath_map, generate__raw_map

# external
import numpy

# # # # #
# CONSTANTS


# PARTICIPANTS
participants = ['p2', 'p2']
stimilua_ist = ['lecture.jpg', 'osfoc.jpg']
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
for ppname in participants:
	
	print("starting data analysis for participant '%s'" % (ppname))

	# BEHAVIOUR
	print("loading behavioural data")
	
	# path
	fp = os.path.join(DATADIR, '%s.tsv' % ppname)

	edfdata = read_opengaze(fp, EDFSTART, EDFSTOP);


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
		print(trialnr)
		print(edfdata[trialnr]);
		# # load image name, saccades, and fixations
		# imgname = stimilua_ist[trialnr]
		# saccades = edfdata[trialnr]['events']['Esac']  # [starttime, endtime, duration, startx, starty, endx, endy]
		# fixations = edfdata[trialnr]['events']['Efix']  # [starttime, endtime, duration, endx, endy]
		#
		# # paths
		# imagefile = os.path.join(IMGDIR, imgname)
		# rawplotfile = os.path.join(pplotdir, "raw_data_%s_%d" % (ppname, trialnr))
		# scatterfile = os.path.join(pplotdir, "fixations_%s_%d" % (ppname, trialnr))
		# scanpathfile = os.path.join(pplotdir, "scanpath_%s_%d" % (ppname, trialnr))
		# heatmapfile = os.path.join(pplotdir, "heatmap_%s_%d" % (ppname, trialnr))
		#
		# # raw data points
		# draw_raw(edfdata[trialnr]['x'], edfdata[trialnr]['y'], DISPSIZE, imagefile=imagefile, savefilename=rawplotfile)
		#
		# # fixations
		# draw_fixations(fixations, DISPSIZE, imagefile=imagefile, durationsize=True, durationcolour=False, alpha=0.5,
		# 			   savefilename=scatterfile)
		#
		# # scanpath
		# draw_scanpath(fixations, saccades, DISPSIZE, imagefile=imagefile, alpha=0.5, savefilename=scanpathfile)
		#
		# # # heatmap
		# # draw_heatmap(fixations, DISPSIZE, imagefile=imagefile, durationweight=True, alpha=0.5, savefilename=heatmapfile)
		#
		#

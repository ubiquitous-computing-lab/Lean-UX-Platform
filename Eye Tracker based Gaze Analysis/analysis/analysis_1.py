
# native
import os

from gazeanalyser._get_gaze_data import read_gaze_data
from gazeanalyser._visual_functions import generate_fixations_map, draw_heatmap, generate_scanpath_map, generate__raw_map

# external
import numpy
DIR = os.path.dirname(__file__)
IMGDIR = os.path.join(DIR, 'imgs')
DATADIR = os.path.join(DIR, 'data')
PLOTDIR = os.path.join(DIR, 'visual_analytics_results')

# DATA FILES
EDFSTART = "start=true" # EDF file trial start message
EDFSTOP = "stop" # EDF file trial end message


# EXPERIMENT SPECS
DISPSIZE = (1024,768) # (px,px)
SCREENSIZE = (39.9,29.9) # (cm,cm)
SCREENDIST = 61.0 # cm

PXPERCM = numpy.mean([DISPSIZE[0]/SCREENSIZE[0],DISPSIZE[1]/SCREENSIZE[1]]) # px/cm

eyetracker_data = read_gaze_data("data/p1.csv", EDFSTART, EDFSTOP);

for trialnr in range(len(eyetracker_data)):
    # load image name, saccades, and fixations
    imgname = "lecture.jpg"

    saccades = eyetracker_data[trialnr]['events']['Esac']  # [starttime, endtime, duration, startx, starty, endx, endy]
    fixations = eyetracker_data[trialnr]['events']['Efix']  # [starttime, endtime, duration, endx, endy]

    print(fixations)
    # paths
    imagefile = os.path.join(IMGDIR, imgname)
    rawplotfile = os.path.join("visual_analytics_results", "raw_data_%s_%d" % ("t1", 1))
    scatterfile = os.path.join("visual_analytics_results", "fixations_%s_%d" % ("t1", 1))
    scanpathfile = os.path.join("visual_analytics_results", "scanpath_%s_%d" % ("t1", 1))
    heatmapfile = os.path.join("visual_analytics_results", "heatmap_%s_%d" % ("t1", 1))



    # raw data points
    generate__raw_map(eyetracker_data[0]['x'], eyetracker_data[0]['y'], DISPSIZE, imagefile=imagefile, savefilename=rawplotfile)

    # fixations
    generate_fixations_map(fixations, DISPSIZE, imagefile=imagefile, durationsize=True, durationcolour=False, alpha=0.5, savefilename =scatterfile)

    # scanpath
    generate_scanpath_map(fixations, saccades, DISPSIZE, imagefile=imagefile, alpha=0.5, savefilename=scanpathfile)

    # heatmap
    # draw_heatmap(fixations, DISPSIZE, imagefile=imagefile, durationweight=True, alpha=0.5, savefilename=heatmapfile)






import os
from gazeanalyser._get_gaze_data import read_gaze_data
from gazeanalyser._visual_functions import generate_fixations_map, generate_scanpath_map, generate__raw_map

def generate_eye_tracker_analytics(stimuli, gazedata):

    DIR = os.path.dirname(__file__)
    IMGDIR = os.path.join(DIR, 'imgs')

    # EXPERIMENT SPECS
    DISPSIZE = [1024,768]

    eyetracker_data = gazedata

    saccades_data  = eyetracker_data[0]['events']['Esac']
    fixations_data = eyetracker_data[0]['events']['Efix']


    imgname = stimuli


    # paths
    imagefile = os.path.join(IMGDIR, imgname)
    rawplotfile = os.path.join("visual_analytics_results", "raw_data_%s_%d" % ("p1", 1))
    scatterfile = os.path.join("visual_analytics_results", "fixations_%s_%d" % ("p1", 1))
    scanpathfile = os.path.join("visual_analytics_results", "scanpath_%s_%d" % ("p1", 1))

    # raw data points
    generate__raw_map(eyetracker_data[0]['x'], eyetracker_data[0]['y'], DISPSIZE, imagefile=imagefile, savefilename=rawplotfile)

    # fixations
    generate_fixations_map(fixations_data, DISPSIZE, imagefile=imagefile, durationsize=True, durationcolour=False, alpha=0.5, savefilename =scatterfile)

    # scanpath
    generate_scanpath_map(fixations_data, saccades_data, DISPSIZE, imagefile=imagefile, alpha=0.5, savefilename=scanpathfile)


if __name__ == '__main__':

    stimuli = "stimuli.jpg"
    gazedata = read_gaze_data("data/eyetracker_data.csv")
    generate_eye_tracker_analytics(stimuli, gazedata)
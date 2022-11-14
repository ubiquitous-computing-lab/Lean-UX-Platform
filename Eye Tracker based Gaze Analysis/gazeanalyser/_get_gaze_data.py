
import copy
import os.path

import numpy
import pandas as pd
from gazeanalyser._compute_visual_metrics import blink_detection, fixation_detection, saccade_detection


def read_gaze_data(filename, missing=0.0, debug=True):
	if debug:
		def message(msg):
			print(msg)
	else:
		def message(msg):
			pass

	# check if the file exists
	if os.path.isfile(filename):
		# open file
		message("opening file '%s'" % filename)
		gaze_data= pd.read_csv(filename)

	# raise exception if the file does not exist
	else:
		raise Exception("Error in data file: file '%s' does not exist" % filename)

	# variables
	gazeData = []
	x = []
	y = []
	time = []
	trackertime = []
	events = {'Sfix':[],'Ssac':[],'Sblk':[],'Efix':[],'Esac':[],'Eblk':[]}
	starttime = 0

	# loop through all gaze data
	for i, data in gaze_data.iterrows():

		try:
			# extract data
			x.append(float(data.LX))
			y.append(float(data.LY))
			time.append(int(1 * float(data.TIME)) - starttime)
			trackertime.append(int(1 * float(data.TIME)))
		except:
			message("line '%s' could not be parsed" % i)
			continue



	trial = {}
	trial['x'] = numpy.array(x)
	trial['y'] = numpy.array(y)
	trial['time'] = numpy.array(time)
	trial['trackertime'] = numpy.array(trackertime)

	trial['events'] = copy.deepcopy(events)

	trial['events']['Sblk'], trial['events']['Eblk'] = blink_detection(trial['x'], trial['y'], trial['trackertime'],missing=missing)
	trial['events']['Sfix'], trial['events']['Efix'] = fixation_detection(trial['x'], trial['y'], trial['trackertime'], missing=missing)
	trial['events']['Ssac'], trial['events']['Esac'] = saccade_detection(trial['x'], trial['y'],trial['trackertime'], missing=missing)

	gazeData.append(trial)


	return gazeData

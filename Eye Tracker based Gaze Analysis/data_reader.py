from gazeanalyser._get_gaze_data import read_gaze_data

# DATA FILES
EDFSTART = "start=true" # EDF file trial start message
EDFSTOP = "stop" # EDF file trial end message


data = read_gaze_data("analysis/data/p1.csv", EDFSTART, EDFSTOP);

# saccades = data[2]['events']['Esac'] # [starttime, endtime, duration, startx, starty, endx, endy]
# fixations = data[2]['events']['Efix'] # [starttime, endtime, duration, endx, endy]
#
# print(saccades,fixations);

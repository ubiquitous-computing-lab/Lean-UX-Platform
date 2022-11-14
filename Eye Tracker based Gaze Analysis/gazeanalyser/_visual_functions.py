import os
import numpy
from matplotlib import pyplot, image

COLS = {	"butter": [	'#fce94f',
					'#edd400',
					'#c4a000'],
		"orange": [	'#fcaf3e',
					'#f57900',
					'#ce5c00'],
		"chocolate": [	'#e9b96e',
					'#c17d11',
					'#8f5902'],
		"chameleon": [	'#8ae234',
					'#73d216',
					'#4e9a06'],
		"skyblue": [	'#729fcf',
					'#3465a4',
					'#204a87'],
		"plum": 	[	'#ad7fa8',
					'#75507b',
					'#5c3566'],
		"scarletred":[	'#ef2929',
					'#cc0000',
					'#a40000'],
		"aluminium": [	'#eeeeec',
					'#d3d7cf',
					'#babdb6',
					'#888a85',
					'#555753',
					'#2e3436'],
		}

# FUNCTIONS
def generate_fixations_map(fixations, dispsize, imagefile=None, durationsize=True, durationcolour=True, alpha=0.5, savefilename=None):

	# FIXATIONS
	fix = parse_fixations(fixations)
	
	# IMAGE
	fig, ax = draw_display(dispsize, imagefile=imagefile)

	# CIRCLES
	# duration weigths
	if durationsize:
		siz = 1 * (fix['dur']/30.0)
	else:
		siz = 1 * numpy.median(fix['dur']/30.0)
	if durationcolour:
		col = fix['dur']
	else:
		col = COLS['chameleon'][2]
	# draw circles
	ax.scatter(fix['x'],fix['y'], s=siz, c=col, marker='o', cmap='jet', alpha=alpha, edgecolors='none')

	# FINISH PLOT
	# invert the y axis, as (0,0) is top left on a display
	ax.invert_yaxis()
	# save the figure if a file name was provided
	if savefilename != None:
		fig.savefig(savefilename)
	
	return fig



def generate__raw_map(x, y, dispsize, imagefile=None, savefilename=None):

	# image
	fig, ax = draw_display(dispsize, imagefile=imagefile)

	# plot raw data points
	ax.plot(x, y, 'o', color=COLS['aluminium'][0], markeredgecolor=COLS['aluminium'][5])

	# invert the y axis, as (0,0) is top left on a display
	ax.invert_yaxis()
	# save the figure if a file name was provided
	if savefilename != None:
		fig.savefig(savefilename)
	
	return fig


def generate_scanpath_map(fixations, saccades, dispsize, imagefile=None, alpha=0.5, savefilename=None):

	# image
	fig, ax = draw_display(dispsize, imagefile=imagefile)

	# FIXATIONS
	# parse fixations
	fix = parse_fixations(fixations)
	# draw fixations
	ax.scatter(fix['x'],fix['y'], s=(1 * fix['dur'] / 30.0), c=COLS['chameleon'][2], marker='o', cmap='jet', alpha=alpha, edgecolors='none')
	# draw annotations (fixation numbers)
	for i in range(len(fixations)):
		ax.annotate(str(i+1), (fix['x'][i],fix['y'][i]), color=COLS['aluminium'][5], alpha=1, horizontalalignment='center', verticalalignment='center', multialignment='center')

	# SACCADES
	if saccades:
		# loop through all saccades
		for st, et, dur, sx, sy, ex, ey in saccades:
			# draw an arrow between every saccade start and ending
			ax.arrow(sx, sy, ex-sx, ey-sy, alpha=alpha, fc=COLS['aluminium'][0], ec=COLS['aluminium'][5], fill=True, shape='full', width=10, head_width=20, head_starts_at_zero=False, overhang=0)

	# invert the y axis, as (0,0) is top left on a display
	ax.invert_yaxis()
	# save the figure if a file name was provided
	if savefilename != None:
		fig.savefig(savefilename)
	
	return fig


# # # # #
# HELPER FUNCTIONS


def draw_display(dispsize, imagefile=None):

	# construct screen (black background)
	_, ext = os.path.splitext(imagefile)
	ext = ext.lower()
	data_type = 'float32' if ext == '.png' else 'uint8'
	screen = numpy.zeros((dispsize[1],dispsize[0],3), dtype=data_type)
	# if an image location has been passed, draw the image
	if imagefile != None:
		# check if the path to the image exists
		if not os.path.isfile(imagefile):
			raise Exception("ERROR in draw_display: imagefile not found at '%s'" % imagefile)
		# load image
		img = image.imread(imagefile)
		# flip image over the horizontal axis
		# (do not do so on Windows, as the image appears to be loaded with
		# the correct side up there; what's up with that? :/)
		if not os.name == 'nt':
			img = numpy.flipud(img)
		# width and height of the image
		w, h = int(len(img[0])), int(len(img))
		# x and y position of the image on the display
		x = int(dispsize[0]/2 - w/2)
		y = int(dispsize[1]/2 - h/2)
		# draw the image on the screen
		screen[y:y+h,x:x+w,:] += img
	# dots per inch
	dpi = 100.0
	# determine the figure size in inches
	figsize = (dispsize[0]/dpi, dispsize[1]/dpi)
	# create a figure
	fig = pyplot.figure(figsize=figsize, dpi=dpi, frameon=False)
	ax = pyplot.Axes(fig, [0,0,1,1])
	ax.set_axis_off()
	fig.add_axes(ax)
	# plot display
	ax.axis([0,dispsize[0],0,dispsize[1]])
	ax.imshow(screen)#, origin='upper')
	
	return fig, ax


def gaussian(x, sx, y=None, sy=None):

	
	# square Gaussian if only x values are passed
	if y == None:
		y = x
	if sy == None:
		sy = sx
	# centers	
	xo = x/2
	yo = y/2
	# matrix of zeros
	M = numpy.zeros([y,x],dtype=float)
	# gaussian matrix
	for i in range(x):
		for j in range(y):
			M[j,i] = numpy.exp(-1.0 * (((float(i)-xo)**2/(2*sx*sx)) + ((float(j)-yo)**2/(2*sy*sy)) ) )

	return M


def parse_fixations(fixations):

	# empty arrays to contain fixation coordinates
	fix = {	'x':numpy.zeros(len(fixations)),
			'y':numpy.zeros(len(fixations)),
			'dur':numpy.zeros(len(fixations))}
	# get all fixation coordinates
	for fixnr in range(len( fixations)):
		stime, etime, dur, ex, ey = fixations[fixnr]
		fix['x'][fixnr] = ex
		fix['y'][fixnr] = ey
		fix['dur'][fixnr] = dur
	
	return fix

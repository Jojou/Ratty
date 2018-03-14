About - Ratty - Java 8, multithreading, modularity, component-service architecture

OBJECTIVE

Solve mazes written in plain text.  Their format will be as follows.

 1 - Walls
 0 - Empty

INPUT:
<WIDTH> <HEIGHT><CR>
<START_X> <START_Y><CR>		(x,y) location of the start. (0,0) is upper left and (width-1,height-1) is lower right
<END_X> <END_Y><CR>		(x,y) location of the end
<HEIGHT> rows where each row has <WIDTH> {0,1} integers space delimited

example:
10 10
1 1
8 8
1 1 1 1 1 1 1 1 1 1
1 0 0 0 0 0 0 0 0 1
1 0 1 0 1 1 1 1 1 1
1 0 1 0 0 0 0 0 0 1
1 0 1 1 0 1 0 1 1 1
1 0 1 0 0 1 0 1 0 1
1 0 1 0 0 0 0 0 0 1
1 0 1 1 1 0 1 1 1 1
1 0 1 0 0 0 0 0 0 1
1 1 1 1 1 1 1 1 1 1

The output will be printed to the console with a path marked by 'X', the start with 'S' and
end with 'E'.

example:

##########
#SXX     #
# #X######
# #XX    #
# ##X# ###
# # X# # #
# # XX   #
# ###X####
# #  XXXE#
##########

INFORMATION

- Running

The entry point to application is MainApp located in com.partridgetech directory.

If this is called without any arguments specified it will automatically use the files located in /mazes directory.
Alternatively, if arguments are supplied it is expected to be a list of directory Strings containing mazes.

- Maze Path Finders

A*
BreadthFirst
DepthFirst

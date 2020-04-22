# Install script for directory: /home/pi/opencv-3.0.0/samples/python2

# Set the install prefix
if(NOT DEFINED CMAKE_INSTALL_PREFIX)
  set(CMAKE_INSTALL_PREFIX "/usr/local")
endif()
string(REGEX REPLACE "/$" "" CMAKE_INSTALL_PREFIX "${CMAKE_INSTALL_PREFIX}")

# Set the install configuration name.
if(NOT DEFINED CMAKE_INSTALL_CONFIG_NAME)
  if(BUILD_TYPE)
    string(REGEX REPLACE "^[^A-Za-z0-9_]+" ""
           CMAKE_INSTALL_CONFIG_NAME "${BUILD_TYPE}")
  else()
    set(CMAKE_INSTALL_CONFIG_NAME "RELEASE")
  endif()
  message(STATUS "Install configuration: \"${CMAKE_INSTALL_CONFIG_NAME}\"")
endif()

# Set the component getting installed.
if(NOT CMAKE_INSTALL_COMPONENT)
  if(COMPONENT)
    message(STATUS "Install component: \"${COMPONENT}\"")
    set(CMAKE_INSTALL_COMPONENT "${COMPONENT}")
  else()
    set(CMAKE_INSTALL_COMPONENT)
  endif()
endif()

# Install shared libraries without execute permission?
if(NOT DEFINED CMAKE_INSTALL_SO_NO_EXE)
  set(CMAKE_INSTALL_SO_NO_EXE "1")
endif()

# Is this installation the result of a crosscompile?
if(NOT DEFINED CMAKE_CROSSCOMPILING)
  set(CMAKE_CROSSCOMPILING "FALSE")
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xsamplesx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/share/OpenCV/samples/python2" TYPE FILE PERMISSIONS OWNER_READ GROUP_READ WORLD_READ FILES
    "/home/pi/opencv-3.0.0/samples/python2/_coverage.py"
    "/home/pi/opencv-3.0.0/samples/python2/_doc.py"
    "/home/pi/opencv-3.0.0/samples/python2/asift.py"
    "/home/pi/opencv-3.0.0/samples/python2/browse.py"
    "/home/pi/opencv-3.0.0/samples/python2/calibrate.py"
    "/home/pi/opencv-3.0.0/samples/python2/camshift.py"
    "/home/pi/opencv-3.0.0/samples/python2/coherence.py"
    "/home/pi/opencv-3.0.0/samples/python2/color_histogram.py"
    "/home/pi/opencv-3.0.0/samples/python2/common.py"
    "/home/pi/opencv-3.0.0/samples/python2/contours.py"
    "/home/pi/opencv-3.0.0/samples/python2/deconvolution.py"
    "/home/pi/opencv-3.0.0/samples/python2/demo.py"
    "/home/pi/opencv-3.0.0/samples/python2/dft.py"
    "/home/pi/opencv-3.0.0/samples/python2/digits.py"
    "/home/pi/opencv-3.0.0/samples/python2/digits_adjust.py"
    "/home/pi/opencv-3.0.0/samples/python2/digits_video.py"
    "/home/pi/opencv-3.0.0/samples/python2/distrans.py"
    "/home/pi/opencv-3.0.0/samples/python2/edge.py"
    "/home/pi/opencv-3.0.0/samples/python2/facedetect.py"
    "/home/pi/opencv-3.0.0/samples/python2/feature_homography.py"
    "/home/pi/opencv-3.0.0/samples/python2/find_obj.py"
    "/home/pi/opencv-3.0.0/samples/python2/fitline.py"
    "/home/pi/opencv-3.0.0/samples/python2/floodfill.py"
    "/home/pi/opencv-3.0.0/samples/python2/gabor_threads.py"
    "/home/pi/opencv-3.0.0/samples/python2/gaussian_mix.py"
    "/home/pi/opencv-3.0.0/samples/python2/grabcut.py"
    "/home/pi/opencv-3.0.0/samples/python2/hist.py"
    "/home/pi/opencv-3.0.0/samples/python2/houghcircles.py"
    "/home/pi/opencv-3.0.0/samples/python2/houghlines.py"
    "/home/pi/opencv-3.0.0/samples/python2/inpaint.py"
    "/home/pi/opencv-3.0.0/samples/python2/kalman.py"
    "/home/pi/opencv-3.0.0/samples/python2/kmeans.py"
    "/home/pi/opencv-3.0.0/samples/python2/lappyr.py"
    "/home/pi/opencv-3.0.0/samples/python2/letter_recog.py"
    "/home/pi/opencv-3.0.0/samples/python2/lk_homography.py"
    "/home/pi/opencv-3.0.0/samples/python2/lk_track.py"
    "/home/pi/opencv-3.0.0/samples/python2/logpolar.py"
    "/home/pi/opencv-3.0.0/samples/python2/morphology.py"
    "/home/pi/opencv-3.0.0/samples/python2/mosse.py"
    "/home/pi/opencv-3.0.0/samples/python2/mouse_and_match.py"
    "/home/pi/opencv-3.0.0/samples/python2/mser.py"
    "/home/pi/opencv-3.0.0/samples/python2/opencv_version.py"
    "/home/pi/opencv-3.0.0/samples/python2/opt_flow.py"
    "/home/pi/opencv-3.0.0/samples/python2/peopledetect.py"
    "/home/pi/opencv-3.0.0/samples/python2/plane_ar.py"
    "/home/pi/opencv-3.0.0/samples/python2/plane_tracker.py"
    "/home/pi/opencv-3.0.0/samples/python2/squares.py"
    "/home/pi/opencv-3.0.0/samples/python2/stereo_match.py"
    "/home/pi/opencv-3.0.0/samples/python2/texture_flow.py"
    "/home/pi/opencv-3.0.0/samples/python2/turing.py"
    "/home/pi/opencv-3.0.0/samples/python2/video.py"
    "/home/pi/opencv-3.0.0/samples/python2/video_threaded.py"
    "/home/pi/opencv-3.0.0/samples/python2/watershed.py"
    )
endif()


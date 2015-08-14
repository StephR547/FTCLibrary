LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

#opencv
OPENCVROOT:= ../../opencv-sdk
OPENCV_CAMERA_MODULES:=on
OPENCV_INSTALL_MODULES:=on
OPENCV_LIB_TYPE:=SHARED
include ${OPENCVROOT}/sdk/native/jni/OpenCV.mk

LOCAL_MODULE    := libftc
LOCAL_SRC_FILES := other.cpp
LOCAL_SHARED_LIBRARIES := opencv_java3
LOCAL_LDLIBS +=  -llog -ldl

include $(BUILD_SHARED_LIBRARY)
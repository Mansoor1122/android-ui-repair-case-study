#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "usage: ./run_test.sh app_package_name script_id"
    exit
fi

script_id=$(($2 + 0))

#compile project containing tests
./gradlew assemble
./gradlew assembleAndroidTest

#remove and install project containing test
adb uninstall "eduandroid"
adb uninstall "eduandroid.test"
adb install "app/build/outputs/apk/debug/app-debug.apk"
adb install "app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk"

#remove and install app under test
adb uninstall $1
if (( $2 < 1000 )); then
	adb install "../../apks/user_contributor/"$2".apk";
else
	adb install "../../apks/repo_contributor/"$2".apk";
fi

#start recording
adb shell screenrecord /sdcard/$2.mp4 &

#run test reproducing the bug report
adb shell am instrument -w -e class eduandroid.Script$2 eduandroid.test/androidx.test.runner.AndroidJUnitRunner

#end recording
adb shell pkill -2 screenrecord

while true; do 
	active=`adb shell ps | grep screenrecord | wc -l`
	echo $active
	if (( $active == 0 )); then
		break
	fi
	sleep 1
done

#get video from device and remove video from device
adb pull /sdcard/$2.mp4 ../../failures/videos/$2.mp4
adb shell rm /sdcard/$2.mp4

#get screenshot from device and remove screenshot from device
adb pull /sdcard/$2.png ../../failures/screenshots/$2.png
adb shell rm /sdcard/$2.png
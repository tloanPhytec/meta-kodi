SUMMARY = "Bento4 is a C++ class library and tools designed to read and write ISO-MP4 files."
HOMEPAGE = "https://github.com/axiomatic-systems/Bento4"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://Documents/LICENSE.txt;md5=2bdfce88f437a0613f41effed74b7061"


inherit cmake pkgconfig

SRC_URI = "git://github.com/xbmc/Bento4;protocol=https;nobranch=1"

PV = "1.6.0-639-Nexus"
SRCREV = "8d7253e1ac05554da4b50d3567706cd42727ecb0"
S = "${WORKDIR}/git"

EXTRA_OECMAKE = "-DBUILD_APPS=OFF -DCMAKE_POSITION_INDEPENDENT_CODE=ON -DCMAKE_BUILD_TYPE=Release"

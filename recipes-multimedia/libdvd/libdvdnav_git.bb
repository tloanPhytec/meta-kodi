SUMMARY = "library for DVD navigation features"
SECTION = "libs/multimedia"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
DEPENDS = "libdvdread"

SRC_URI = "git://github.com/xbmc/libdvdnav.git;protocol=https;branch=nexus"

SRCREV = "ce25d1fd0c97ee74d1647af3ffea75f6faf76057"
S = "${WORKDIR}/git"
PV = "6.1.1-Next-Nexus-Alpha2"

inherit autotools lib_package binconfig pkgconfig

CONFIGUREOPTS:remove = "--disable-silent-rules"


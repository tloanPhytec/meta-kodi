SUMMARY = "Kodi Media Center"
DESCRIPTION = "Kodi is an award-winning free and open source home theater/media \ 
center software and entertainment hub for digital media. With its beautiful \
interface and powerful skinning engine, it's available for Android, BSD, Linux, \
macOS, iOS and Windows."

HOMEPAGE = "https://kodi.tv/"
BUGTRACKER = "https://github.com/xbmc/xbmc/issues"

require kodi.inc
inherit ccache cmake pkgconfig gettext python3native

SRC_URI:append = " \
	file://0001-mysqldataset-we-use-mysql-includedir-name-for-mari.patch \
	file://0001-AEELDParser.cpp-fix-rtrim-function-for-clang.patch \
	file://libreelec/kodi-100.03-disable-online-check.patch \
	file://libreelec/kodi-995.10-devinputmappings.patch \
	file://libreelec/kodi-999.15-disable-using-tv-menu-language-by-default.patch \
	file://0001-FindLibDvd.cmake-build-with-external-source.patch \
	file://drmprime-filter/0001-WIP-DVDVideoCodecDRMPRIME-add-support-for-filters.patch \
	file://drmprime-filter/0002-WIP-DRMPRIME-deinterlace-filter.patch \
	file://drmprime-filter/0003-DVDVideoCodecDRMPRIME-Avoid-exception-with-AV_PIX_FM.patch \
	file://drmprime-filter/0004-DVDVideoCodecDRMPRIME-Leave-deinterlace-filter-activ.patch \
	file://drmprime-filter/0005-SetVideoInterlaced-Set-and-unset-deinterlace-method-.patch \
	file://drmprime-filter/0006-DVDVideoCodecDRMPRIME-Close-deinterlace-filter-on-er.patch \
"

SRC_URI:append:rpi = " \
	file://rpi/kodi-001-deinterlace.patch \
	file://kodi-002-PR20006-gbm-colorspace-and-bits-per-pixel.patch \
"

DEPENDS += " \
  alsa-lib \
  avahi \
  bzip2 \
  crossguid \
  curl \
  dbus \
  expat \
  ffmpeg \
  flatbuffers \
  flatbuffers-native \
  fmt \
  fontconfig \
  freetype \
  fribidi \
  fstrcmp \
  giflib \
  gmp \
  gnutls \
  googletest-native \
  gperf-native \
  harfbuzz \
  kodi-tools-jsonschemabuilder-native \
  kodi-tools-texturepacker-native \
  libass \
  libcdio \
  libcec \
  libdrm \
  libdvdnav \
  libdvdread \
  libevdev \
  libffi \
  libgcrypt \
  libgpg-error \
  libinput \
  libjpeg-turbo \
  libmicrohttpd \
  libnfs \
  libplist \
  libpng \
  libtinyxml \
  libunistring \
  libusb \
  libxml2 \
  libxslt \
  lsb-release \
  lzo \
  nasm-native \
  nettle \
  openssl \
  p8platform \
  pcre \
  rapidjson \
  spdlog \
  sqlite3 \
  swig-native \
  taglib \
  virtual/egl \
  xz \
  yasm-native \
  zlib \
"

PACKAGECONFIG ?= " \
  ${@bb.utils.filter('DISTRO_FEATURES', 'systemd bluetooth lirc pulseaudio pipewire samba', d)} \
  ${@bb.utils.filter('KODIGRAPHICALBACKEND', 'gbm wayland x11', d)} \
  ${@bb.utils.contains('TOOLCHAIN', 'clang', 'clang', '', d)} \
  ${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-lld', 'lld', '', d)} \
  airtunes \
  joystick \
  lcms \
  lto \
"

PACKAGECONFIG:append:x86 = " vaapi"
PACKAGECONFIG:append:x86-64 = " vaapi"

# Core windowing system choices
PACKAGECONFIG[gbm] = "-DCORE_PLATFORM_NAME=gbm -DAPP_RENDER_SYSTEM=gles,,libxkbcommon virtual/libgbm"
PACKAGECONFIG[wayland] = "-DCORE_PLATFORM_NAME=wayland -DAPP_RENDER_SYSTEM=gles,,wayland wayland-native waylandpp waylandpp-native libxkbcommon virtual/libgles2"
PACKAGECONFIG[x11] = "-DCORE_PLATFORM_NAME=x11 -DAPP_RENDER_SYSTEM=gl,,libxmu libxrandr glew virtual/libgl"

# Features
PACKAGECONFIG[airtunes] = "-DENABLE_AIRTUNES=ON,-DENABLE_AIRTUNES=OFF,shairplay"
PACKAGECONFIG[bluetooth] = ",,bluez5"
PACKAGECONFIG[doxygen] = ",,doxygen-native"
PACKAGECONFIG[dvdcss] = "-DENABLE_DVDCSS=ON,-DENABLE_DVDCSS=OFF,libdvdcss"
PACKAGECONFIG[joystick] = ",,,kodi-addon-peripheral-joystick"
PACKAGECONFIG[lcms] = ",,lcms"
PACKAGECONFIG[lirc] = ",,lirc"
PACKAGECONFIG[mysql] = "-DENABLE_MYSQLCLIENT=ON,-DENABLE_MYSQLCLIENT=OFF,mysql5"
PACKAGECONFIG[mariadb] = "-DENABLE_MARIADBCLIENT=ON -DMARIADBCLIENT_INCLUDE_DIR=${STAGING_INCDIR}/mysql,-DENABLE_MARIADBCLIENT=OFF,mariadb"
PACKAGECONFIG[optical] = "-DENABLE_OPTICAL=ON,-DENABLE_OPTICAL=OFF,libudfread libbluray"
PACKAGECONFIG[pulseaudio] = "-DENABLE_PULSEAUDIO=ON,-DENABLE_PULSEAUDIO=OFF,pulseaudio"
PACKAGECONFIG[pipewire] = "-DENABLE_PIPEWIRE=ON,-DENABLE_PIPEWIRE=OFF,pipewire"
PACKAGECONFIG[samba] = ",,samba"
PACKAGECONFIG[systemd] = ",,,kodi-systemd-service"
PACKAGECONFIG[vaapi] = "-DENABLE_VAAPI=ON,-DENABLE_VAAPI=OFF,libva intel-vaapi-driver,intel-vaapi-driver"
PACKAGECONFIG[vdpau] = "-DENABLE_VDPAU=ON,-DENABLE_VDPAU=OFF,libvdpau,mesa-vdpau-drivers"
PACKAGECONFIG[vorbis] = ",,libvorbis"


# Compilation tunes
PACKAGECONFIG[lld] = "-DENABLE_LLD=ON,-DENABLE_LLD=OFF"
PACKAGECONFIG[clang] = "-DENABLE_CLANGFORMAT=ON -DENABLE_CLANGTIDY=ON,-DENABLE_CLANGFORMAT=OFF -DENABLE_CLANGTIDY=OFF"
PACKAGECONFIG[lto] = "-DUSE_LTO=${@oe.utils.cpu_count()},-DUSE_LTO=OFF"
PACKAGECONFIG[testing] = "-DENABLE_TESTING=ON,-DENABLE_TESTING=0FF,googletest"

# MIPS
LDFLAGS += "${TOOLCHAIN_OPTIONS}"
LDFLAGS:append:mipsarch = " -latomic -lpthread"
EXTRA_OECMAKE:append:mipsarch = " -DWITH_ARCH=${TARGET_ARCH}"

RUNTIME_NM = "${@bb.utils.contains('RUNTIME', 'llvm', '${TARGET_PREFIX}llvm-nm', '${TARGET_PREFIX}gcc-nm', d)}"

EXTRA_OECMAKE = " \
    -DUSE_INTERNAL_LIBS=OFF \
    -DWITH_TEXTUREPACKER=${STAGING_BINDIR_NATIVE}/TexturePacker \
    -DWITH_JSONSCHEMABUILDER=${STAGING_BINDIR_NATIVE}/JsonSchemaBuilder \
    -DJava_JAVA_EXECUTABLE=/usr/bin/java \
    -DCLANG_TIDY_EXECUTABLE=${STAGING_BINDIR_NATIVE}/clang-tidy \
    -DCLANG_FORMAT_EXECUTABLE=${STAGING_BINDIR_NATIVE}/clang-format \
    \
    -DENABLE_STATIC_LIBS=FALSE \
    -DCMAKE_NM=${STAGING_BINDIR_NATIVE}/${TARGET_SYS}/${RUNTIME_NM} \
    \
    -DFFMPEG_PATH=${STAGING_DIR_TARGET} \
    -DENABLE_INTERNAL_CROSSGUID=OFF \
    -DENABLE_INTERNAL_RapidJSON=OFF \
    -DENABLE_INTERNAL_FLATBUFFERS=OFF \
    -DWAYLANDPP_PROTOCOLS_DIR=${STAGING_DATADIR}/waylandpp/protocols \
    -DCMAKE_BUILD_TYPE=RelWithDebInfo \
"

do_configure:prepend() {
	# Ensure 'nm' can find the lto plugins 
	liblto=$(find ${STAGING_DIR_NATIVE} -name "liblto_plugin.so.0.0.0")
	mkdir -p ${STAGING_LIBDIR_NATIVE}/bfd-plugins
	ln -sf $liblto ${STAGING_LIBDIR_NATIVE}/bfd-plugins/liblto_plugin.so
}

FILES:${PN} += "${datadir}/metainfo ${datadir}/xsessions ${datadir}/icons ${libdir}/xbmc ${datadir}/xbmc ${libdir}/firewalld"
FILES:${PN}-dbg += "${libdir}/kodi/.debug ${libdir}/kodi/*/.debug ${libdir}/kodi/*/*/.debug ${libdir}/kodi/*/*/*/.debug"

RRECOMMENDS:${PN} = " \
  ${KODI_PLUGINS_INSTALL} \
  libcec \
  libcurl \
  libnfs \
  python3 \
  python3-compression \
  python3-ctypes \
  python3-crypt \
  python3-datetime \
  python3-db \
  python3-image \
  python3-difflib \
  python3-distutils \
  python3-html \
  python3-json \
  python3-multiprocessing \
  python3-netclient \
  python3-pillow \
  python3-pycryptodome \
  python3-regex \
  python3-shell \
  python3-six \
  python3-setuptools \
  python3-sqlite3 \
  python3-xmlrpc \
  tzdata-africa \
  tzdata-americas \
  tzdata-antarctica \
  tzdata-arctic \
  tzdata-asia \
  tzdata-atlantic \
  tzdata-australia \
  tzdata-europe \
  tzdata-pacific \
  xkeyboard-config \
"

RRECOMMENDS:${PN}:append:libc-glibc = " \
  glibc-charmap-ibm850 \
  glibc-gconv-ibm850 \
  glibc-charmap-ibm437 \
  glibc-gconv-ibm437 \
  glibc-gconv-unicode \
  glibc-gconv-utf-32 \
  glibc-charmap-utf-8 \
  glibc-localedata-en-us \
"

KODI_PLUGINS_INSTALL ??= " \
  kodi-addon-inputstream-adaptive \
  kodi-addon-inputstream-rtmp \
  kodi-addon-inputstream-ffmpegdirect \
  kodi-addon-inputstreamhelper \
  kodi-addon-peripheral-joystick \
  kodi-addon-pvr-iptvsimple \
  kodi-addon-vfs-rar \
  kodi-addon-vfs-libarchive \
"

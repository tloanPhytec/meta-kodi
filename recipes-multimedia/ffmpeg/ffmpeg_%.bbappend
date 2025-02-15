FILESEXTRAPATHS:prepend := "${THISDIR}/ffmpeg:"

SRC_URI = " \
	git://github.com/xbmc/FFmpeg.git;protocol=https;branch=release/4.4-kodi \
	file://ffmpeg-support-dav1d-1-0-0.patch \
	file://ffmpeg-openssl3.patch \
	file://libreelec/ffmpeg-001-libreelec.patch \
"

SRC_URI:append:rpi = "	\
	file://rpi/ffmpeg-001-rpi.patch \
"

SRC_URI:append:rockchip = " \
	file://v4l2-drmprime/ffmpeg-001-v4l2-drmprime.patch \
	file://v4l2-request/ffmpeg-001-v4l2-request.patch \
	file://rockchip/ffmpeg-0002-WIP-deint-filter.patch \
	file://rockchip/ffmpeg-0003-libavfilter-v4l2deinterlace-dequeue-both-destination.patch \
	file://rockchip/ffmpeg-0004-v4l2request-hevc-increase-max-slices.patch \
	file://rockchip/ffmpeg-0006-deint_v4l2m2m-increase-input-and-output-buffers.patch \
	file://rockchip/ffmpeg-0006-libavfilter-v4l2deinterlace-support-more-formats-aut.patch \
"

S = "${WORKDIR}/git"
PV = "4.4.1-N-Alpha1"
SRCREV = "293e067b0c0f592628ee0de71769ed2e9c3d07f2"

PACKAGECONFIG[dav1d] = "--enable-libdav1d,--disable-libdav1d,dav1d"
PACKAGECONFIG[libass] = "--enable-libass,--disable-libass,libass"
PACKAGECONFIG[libfontconfig] = "--enable-libfontconfig,--disable-libfontconfig,fontconfig"
PACKAGECONFIG[libfreetype] = "--enable-libfreetype,--disable-libfreetype,freetype"
PACKAGECONFIG[libopus] = "--enable-libopus,--disable-libopus,libopus"
PACKAGECONFIG[librtmp] = "--enable-librtmp,--disable-librtmp,rtmpdump"
PACKAGECONFIG[libv4l2] = "--enable-libv4l2,--disable-libv4l2,v4l-utils"
PACKAGECONFIG[pulseaudio] = "--enable-libpulse,--disable-libpulse,pulseaudio"
PACKAGECONFIG[v4l2-m2m] = "--enable-v4l2_m2m --enable-libdrm,--disable-v4l2_m2m,libdrm"
PACKAGECONFIG[webp] = "--enable-libwebp,--disable-libwebp,libwebp"
PACKAGECONFIG[zimg] = "--enable-libzimg,--disable-libzimg,zimg"
PACKAGECONFIG[libxml2] = "--enable-libxml2,--disable-libxml2,libxml2"
PACKAGECONFIG[vidstab] = "--enable-libvidstab --enable-gpl,--disable-libvidstab,vid.stab"
PACKAGECONFIG[x265] = "--enable-libx265,--disable-libx265,x265"

PACKAGECONFIG:append = " \
	dav1d \
	fdk-aac \
	librtmp \
	libass \
	libfontconfig \
	libfreetype \
	libopus \
	libv4l2 \
	libvorbis \
	libxml2 \
	mp3lame \
	openssl \
	pulseaudio \
	vidstab \
	vpx \
	webp \
	x264 \
"

PACKAGECONFIG:append:arm = "v4l2-m2m v4l2-request"
PACKAGECONFIG:append:aarch64 = "v4l2-m2m v4l2-request"

PACKAGECONFIG:append:x86 = " vaapi"
PACKAGECONFIG:append:x86-64 = " vaapi"

EXTRA_FFCONF = " \
	--prefix=${prefix} \
	--disable-static \
	--disable-runtime-cpudetect \
	--disable-doc \
	--disable-fast-unaligned \	
	--disable-htmlpages \
	--disable-manpages \
	--disable-podpages \
	--disable-txtpages \
	--disable-debug \
	--disable-altivec \
	--enable-inline-asm \
	--enable-asm \
	--enable-muxers \
	--enable-encoders \
	--enable-decoders \
	--enable-demuxers \
	--enable-ffprobe \
	--enable-nonfree \
	--enable-parsers \
	--enable-bsfs \
	--enable-protocols \
	--enable-indevs \
	--enable-outdevs \
	--enable-filters \
	--enable-hwaccels \
	--enable-optimizations \
	--enable-pthreads \
	--enable-postproc \
	--enable-network \
	--enable-swscale \
	--disable-gray \
	--enable-swscale-alpha \
	--disable-small \
	--enable-dct \
	--enable-fft \
	--enable-mdct \
	--enable-rdft \
	--disable-crystalhd \
"

EXTRA_FFCONF:append:arm = " \
	--disable-amd3dnow \
	--disable-amd3dnowext \
	--disable-mmx \
	--disable-mmxext \
	--disable-sse \
	--disable-sse2 \
	--disable-sse3 \
	--disable-ssse3 \
	--disable-sse4 \
	--disable-sse42 \
	--disable-x86asm \
	--disable-avx \
	--disable-xop \
	--disable-fma3 \
	--disable-fma4 \
	--disable-avx2 \
	--enable-armv6 \
	--enable-armv6t2 \
	--enable-vfp \
	--enable-neon \
"

EXTRA_FFCONF:append:aarch64 = " \
	--disable-amd3dnow \
	--disable-amd3dnowext \
	--disable-mmx \
	--disable-mmxext \
	--disable-sse \
	--disable-sse2 \
	--disable-sse3 \
	--disable-ssse3 \
	--disable-sse4 \
	--disable-sse42 \
	--disable-x86asm \
	--disable-avx \
	--disable-xop \
	--disable-fma3 \
	--disable-fma4 \
	--disable-avx2 \
	--enable-armv8 \
	--enable-vfp \
	--enable-neon \
"

EXTRA_FFCONF:append:rpi = " \
	--disable-rpi \
	--enable-sand \
	--disable-mmal \
	--disable-hwaccel=h264_v4l2request \
	--disable-hwaccel=mpeg2_v4l2request \
	--disable-hwaccel=vp8_v4l2request \
	--disable-hwaccel=vp9_v4l2request \
"

LICENSE_FLAGS_ACCEPTED = "commercial"

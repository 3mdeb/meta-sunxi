DESCRIPTION = "Mali OpenGL ES userspace libraries for Allwinner SoCs "
SECTION = "libs"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${S}/EULA%20for%20Mali%20400MP%20_AW.pdf;md5=495406406519c27072a3e6f1e825c0a8"

SRC_URI = " \
    git://github.com/3mdeb/mali-blobs.git;protocol=https;branch=wayland_includes \
    file://egl.pc \
    file://wayland-egl.pc \
    file://glesv1_cm.pc \
    file://glesv1.pc \
    file://glesv2.pc \
    file://gbm.pc \
    "

S = "${WORKDIR}/git"

SRCREV = "${AUTOREV}"

# X11 dependencies
X11_RDEPENDS = "libxdamage libxext libx11 libdrm libxfixes"
X11_DEPENDS = "libxdamage libxext virtual/libx11 libdrm libxfixes"

# Wayland dependencies
WL_RDEPENDS = ""
WL_DEPENDS = "virtual/mesa"

# framebuffer dependencies
DFB_RDEPENDS = ""
DFB_DEPENDS = ""

DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '${WL_DEPENDS}', '', d)}"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '${X11_DEPENDS}', '', d)}"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'directfb', '${DFB_DEPENDS}', '', d)}"

RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '${WL_RDEPENDS}', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'x11', '${X11_RDEPENDS}', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'directfb', '${DFB_RDEPENDS}', '', d)}"

PROVIDES += "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'virtual/egl virtual/libgles1 virtual/libgles2', '', d)}"
PROVIDES += "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'virtual/libwayland-egl virtual/libgbm', '', d)}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

# Shared libMali object is not version and we need to ship the .so object into
# the runtime package
FILES_SOLIBSDEV_remove = " \
    libMali.so \
    libEGL.so \
    libgbm.so \
    libGLESv1_CM.so \
    libGLESv2.so \
    libwayland-egl.so \
    "

FILES_${PN} += "${libdir}/libMali.so \
    ${libdir}/libEGL.so \
    ${libdir}/libgbm.so \
    ${libdir}/libGLESv1_CM.so \
    ${libdir}/libGLESv2.so \
    ${libdir}/libwayland-egl.so \
    "


RREPLACES_${PN} = "libegl libegl1 libgles1 libgles2 libgbm"
RPROVIDES_${PN} = "libegl libegl1 libgles1 libgles2 libgbm"
RCONFLICTS_${PN} = "libegl libegl1 libgles1 libgles2 libgbm"

PACKAGE_ARCH = "${MACHINE_ARCH}"
PACKAGES = "${PN} ${PN}-dev"

### default configuration
# x11_dma_buf  for x11 DISTRO_FEATURE
# wayland for wayland DISTRO_FEATURE
# fbdev for directfb DISTRO_FEATURE
MALI_EGL_TYPE ?= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11_dma_buf', '', d)}${@bb.utils.contains('DISTRO_FEATURES', 'directfb', 'fbdev', '', d)}${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)}"
MALI_EGL_ARCH ?= "arm"

# skip sanity checks we can't do anything about due to the closed blobs nature
INSANE_SKIP_${PN} = "ldflags dev-so"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

SUMMARY = "sunxi mali kernel driver for mainline Linux"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

inherit module

SRC_URI = "git://github.com/3mdeb/sunxi-mali.git;protocol=https;branch=r6p2-patched"

PV = "r6p2"

SRCREV = "69fb37c6e5e3d429a9a84717b82a9bd0d9f970a3"

# # common patches
# SRC_URI += " \
#     file://0001-makefile-Add-install-target-and-build-the-module-by-.patch \
#     file://0002-mali-Support-building-against-4.6.patch \
#     file://0003-mali-Support-building-against-4.8.patch \
#     file://0004-mali-Print-the-mali-version-at-probe.patch \
#     file://0005-mali-Add-sunxi-platform.patch \
#     file://0007-mali-support-building-against-4.10.patch \
#     file://0008-mali-support-building-against-4.11.patch \
#     file://0010-mali-support-building-against-4.12.patch \
#     file://0012-mali-support-building-against-4.14.patch \
#     file://0015-Enable-parallel-building-passing-variable-to-Makefile.patch \
#     "
# 
# 
# # r6p2 patches
# SRC_URI += " \
#     file://0006-mali-Allow-devfreq-to-run-without-power-models.patch \
#     file://0009-mali-Fix-user-memory-domain-fault.patch              \
#     file://0011-mali-support-building-against-4.13.patch             \
#     file://0013-mali-support-building-against-4.15.patch             \
#     file://0014-mali-Make-devfreq-optional.patch                     \
#     file://0016-mali-support-building-against-4.16.patch             \
#     "

SRC_URI += "file://0001-Makefile-use-KERNEL_BUILD_ARTIFACTS_DIR-for-.config-.patch"

S = "${WORKDIR}/git/r6p2/src/devicedrv/mali"

export KDIR = "${KERNEL_SRC}"
export KERNEL_BUILD_ARTIFACTS_DIR = "${STAGING_KERNEL_BUILDDIR}"

EXTRA_OEMAKE +=" \
    USING_UMP=0 \
    BUILD=release \
    USING_PROFILING=0 \
    MALI_PLATFORM=sunxi \
    USING_DVFS=1 \
    USING_DEVFREQ=1 \
    "

MODULES_INSTALL_TARGET = "install"

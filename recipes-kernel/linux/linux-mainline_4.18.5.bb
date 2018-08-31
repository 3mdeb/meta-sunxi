SECTION = "kernel"
DESCRIPTION = "Mainline Linux kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
COMPATIBLE_MACHINE = "(sun4i|sun5i|sun7i|sun8i|sun50i)"

inherit kernel

require linux.inc

# Since we're not using git, this doesn't make a difference, but we need to fill
# in something or kernel-yocto.bbclass will fail.
KBRANCH ?= "master"

# Pull in the devicetree files into the rootfs
RDEPENDS_${KERNEL_PACKAGE_NAME}-base += "kernel-devicetree"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

S = "${WORKDIR}/linux-${PV}"

SRC_URI[md5sum] = "22851fe6c82db6673a844bbb7c62df67"
SRC_URI[sha256sum] = "fb090a3680eddf6f10bf895bc3075bd3f830e3d2429ce469982db5a28df647bd"

SRC_URI = "https://www.kernel.org/pub/linux/kernel/v4.x/linux-${PV}.tar.xz \
        file://defconfig \
        "

# mandatory when providing defocnfig file from "make savedefconfig"
KCONFIG_MODE="--alldefconfig"

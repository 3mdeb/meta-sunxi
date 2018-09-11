require mali.inc

DEPENDS += "patchelf-native"

do_install() {
    # install headers
    install -d -m 0755 ${D}${includedir}/EGL
    install -m 0644 ${S}/include/${MALI_EGL_TYPE}/EGL/*.h ${D}${includedir}/EGL/
    install -d -m 0755 ${D}${includedir}/GLES
    install -m 0644 ${S}/include/${MALI_EGL_TYPE}/GLES/*.h ${D}${includedir}/GLES/
    install -d -m 0755 ${D}${includedir}/GLES2/
    install -m 0644 ${S}/include/${MALI_EGL_TYPE}/GLES2/*.h ${D}${includedir}/GLES2/
    install -d -m 0755 ${D}${includedir}/KHR
    install -m 0644 ${S}/include/${MALI_EGL_TYPE}/KHR/*.h ${D}${includedir}/KHR/
    if [ "${MALI_EGL_TYPE}" = "wayland" ]; then
      install -m 0644 ${S}/include/${MALI_EGL_TYPE}/gbm.h ${D}${includedir}/
    fi

    # install pkgconfig files
    install -d -m 0755 ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/egl.pc ${D}${libdir}/pkgconfig/
    install -m 0644 ${WORKDIR}/glesv2.pc ${D}${libdir}/pkgconfig/
    install -m 0644 ${WORKDIR}/glesv1.pc ${D}${libdir}/pkgconfig/
    install -m 0644 ${WORKDIR}/glesv1_cm.pc ${D}${libdir}/pkgconfig/
    if [ "${MALI_EGL_TYPE}" = "wayland" ]; then
      install -m 0644 ${WORKDIR}/wayland-egl.pc ${D}${libdir}/pkgconfig/
      install -m 0644 ${WORKDIR}/gbm.pc ${D}${libdir}/pkgconfig/
    fi

    # install Mali library
    install -d -m 0755 ${D}${libdir}
    install -m 0644 ${S}/${PV}/${MALI_EGL_ARCH}/${MALI_EGL_TYPE}/libMali.so ${D}${libdir}
    # The DT_SONAME is not compiled-in the blobs. Below line fixes a bunch of
    # "file-rdeps" sanity errors. Example issue with explaination:
    # https://github.com/96boards/meta-96boards/issues/195
    patchelf --set-soname libMali.so ${D}${libdir}/libMali.so

    # install links to Mali library
    find . -type l -exec cp -a --no-preserve=ownership {} ${D}${libdir} \;

}

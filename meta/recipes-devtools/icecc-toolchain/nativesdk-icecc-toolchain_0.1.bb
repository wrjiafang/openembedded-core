# Copyright (c) 2018 Joshua Watt, Garmin International,Inc.
# Released under the MIT license (see COPYING.MIT for the terms)
SUMMARY = "Generates Icecream toolchain for SDK"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/icecc-env.sh;beginline=2;endline=20;md5=aafdb7bc2aa7ac5d039fda0c8733983c"

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "\
    file://icecc-env.sh \
    file://icecc-setup.sh \
    "

inherit nativesdk

ENV_NAME="${DISTRO}-${TCLIBC}-${SDK_ARCH}-${TUNE_PKGARCH}-${DISTRO_VERSION}.tar.gz"

do_compile() {
}

do_install() {
    install -d ${D}${SDKPATHNATIVE}${datadir}/icecream/bin

    install -d ${D}${SDKPATHNATIVE}/environment-setup.d/
    install -m 0644 ${WORKDIR}/icecc-env.sh ${D}${SDKPATHNATIVE}/environment-setup.d/
    sed -i ${D}${SDKPATHNATIVE}/environment-setup.d/icecc-env.sh \
        -e "s,@TOOLCHAIN_ENV@,${ENV_NAME},g"

    install -d ${D}${SDKPATHNATIVE}/post-relocate-setup.d/
    install -m 0755 ${WORKDIR}/icecc-setup.sh ${D}${SDKPATHNATIVE}/post-relocate-setup.d/
    sed -i ${D}${SDKPATHNATIVE}/post-relocate-setup.d/icecc-setup.sh \
        -e "s,@TOOLCHAIN_ENV@,${ENV_NAME},g"
}

PACKAGES = "${PN}"
FILES_${PN} = "${SDKPATHNATIVE}"
RDEPENDS_${PN} += "nativesdk-icecc-create-env"


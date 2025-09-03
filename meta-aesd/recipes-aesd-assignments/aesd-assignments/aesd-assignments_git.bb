# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-moskwitto.git;protocol=ssh;branch=main"
PV = "1.0+git${SRCPV}"
SRCREV = "615262b3a8c2f982e9afe26d805bb2e656df055f"

S = "${WORKDIR}/git/server"

# This ensures the app binary and init script are installed
FILES:${PN} += "${bindir}/aesdsocket ${sysconfdir}/init.d/aesdsocket"

# Compiler and linker flags to ensure PIE-safe builds
TARGET_CFLAGS += "-fPIC"
TARGET_CXXFLAGS += "-fPIC"
TARGET_LDFLAGS += "-pthread -lrt"

# 💡 Disable building tests in case CMake tries to
EXTRA_OECMAKE += "-DBUILD_TESTING=OFF"

# If you're using CMake, uncomment this:
# inherit cmake

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdsocket ${D}${bindir}/

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d/aesdsocket
}


#include <jni.h>
#include <string.h>
#include <stdint.h>
#include <libcob.h>
#include "com_cajario_bridge_CobolBridge.h"

#pragma pack(push, 1)
struct cajario_payload {
    uint64_t tarjeta;  // PIC 9(18) COMP-5
    uint16_t pin;      // PIC 9(04) COMP-5
    double monto;      // USAGE COMP-2
    char status[2];    // PIC X(02)
};
#pragma pack(pop)

extern void cajario_core(struct cajario_payload *data);

JNIEXPORT jstring JNICALL Java_com_cajario_bridge_CobolBridge_callCajaRio
  (JNIEnv *env, jobject obj, jlong tarjeta, jint pin, jdouble monto) {
    
    struct cajario_payload data;
    memset(&data, 0, sizeof(data));
    
    data.tarjeta = (uint64_t)tarjeta;
    data.pin = (uint16_t)pin;
    data.monto = (double)monto;
    
    data.status[0] = 'X'; 
    data.status[1] = 'X';

    cob_init(0, NULL);
    cajario_core(&data);

    char res[3];
    res[0] = data.status[0];
    res[1] = data.status[1];
    res[2] = '\0';

    return (*env)->NewStringUTF(env, res);
}
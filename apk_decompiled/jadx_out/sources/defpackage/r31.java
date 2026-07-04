package defpackage;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class r31 implements SensorEventListener {
    public final /* synthetic */ s31 a;

    public r31(s31 s31Var) {
        this.a = s31Var;
    }

    @Override // android.hardware.SensorEventListener
    public final void onSensorChanged(SensorEvent sensorEvent) {
        s31 s31Var = this.a;
        ik0 ik0Var = s31Var.b;
        ek0 ek0Var = s31Var.a;
        if (sensorEvent != null && sensorEvent.sensor.getType() == 1) {
            float[] fArr = sensorEvent.values;
            float f = fArr[0];
            float f2 = fArr[1];
            float sqrt = (float) Math.sqrt((f2 * f2) + (f * f) + 96.23611f);
            ek0Var.h((((float) Math.atan2(f2, f)) * 57.29578f * 0.5f) + (ek0Var.g() * 0.5f));
            ik0Var.setValue(new ch0(ch0.g(ch0.h(((ch0) ik0Var.getValue()).a, 0.5f), ch0.h((Float.floatToRawIntBits(f2 / sqrt) & 4294967295L) | (Float.floatToRawIntBits(f / sqrt) << 32), 0.5f))));
        }
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {
    }
}

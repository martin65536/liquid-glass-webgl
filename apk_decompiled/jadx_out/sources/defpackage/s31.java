package defpackage;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class s31 {
    public final ek0 a;
    public final ik0 b;
    public final SensorManager c;
    public final Sensor d;
    public final r31 e;

    public s31(Context context) {
        context.getClass();
        this.a = new ek0(45.0f);
        this.b = n30.B(new ch0(0L));
        Object systemService = context.getSystemService("sensor");
        systemService.getClass();
        SensorManager sensorManager = (SensorManager) systemService;
        this.c = sensorManager;
        this.d = sensorManager.getDefaultSensor(1);
        this.e = new r31(this);
    }
}

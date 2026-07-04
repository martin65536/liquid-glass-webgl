package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mb implements ob {
    public final /* synthetic */ int b;

    public /* synthetic */ mb(int i) {
        this.b = i;
    }

    @Override // defpackage.ob
    public final float a(float f, float f2, float f3) {
        boolean z;
        switch (this.b) {
            case 0:
                ob.a.getClass();
                float f4 = f2 + f;
                if ((f >= 0.0f && f4 <= f3) || (f < 0.0f && f4 > f3)) {
                    return 0.0f;
                }
                float f5 = f4 - f3;
                if (Math.abs(f) >= Math.abs(f5)) {
                    return f5;
                }
                return f;
            default:
                float abs = Math.abs((f2 + f) - f);
                if (abs <= f3) {
                    z = true;
                } else {
                    z = false;
                }
                float f6 = (0.3f * f3) - (0.0f * abs);
                float f7 = f3 - f6;
                if (z && f7 < abs) {
                    f6 = f3 - abs;
                }
                return f - f6;
        }
    }

    @Override // defpackage.ob
    public final ay0 b() {
        switch (this.b) {
            case 0:
            default:
                ob.a.getClass();
                return nb.b;
        }
    }
}

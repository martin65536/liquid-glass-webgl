package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hs {
    public final float a;
    public final float b;

    public hs(float f, mm mmVar) {
        this.a = f;
        float B = mmVar.B();
        float f2 = is.a;
        this.b = B * 386.0878f * 160.0f * 0.84f;
    }

    public final gs a(float f) {
        double b = b(f);
        double d = is.a;
        double d2 = d - 1.0d;
        return new gs(f, (float) (Math.exp((d / d2) * b) * this.a * this.b), (long) (Math.exp(b / d2) * 1000.0d));
    }

    public final double b(float f) {
        float[] fArr = j5.a;
        return Math.log((Math.abs(f) * 0.35f) / (this.a * this.b));
    }
}

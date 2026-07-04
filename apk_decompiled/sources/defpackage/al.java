package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class al {
    public final hk a;
    public final ie b;
    public final float c;
    public final kv d;
    public final gv e;
    public final lv f;
    public final ay0 g;
    public final ay0 h;
    public final ay0 i;
    public final ay0 j;
    public final ay0 k;
    public final y6 l;
    public final y6 m;
    public final y6 n;
    public final y6 o;
    public final y6 p;
    public final nf0 q;
    public final u41 r;
    public final tz0 s;

    public al(hk hkVar, float f, ie ieVar, float f2, float f3, kv kvVar, gv gvVar, lv lvVar) {
        hkVar.getClass();
        this.a = hkVar;
        this.b = ieVar;
        this.c = f3;
        this.d = kvVar;
        this.e = gvVar;
        this.f = lvVar;
        this.g = new ay0(1.0f, 1000.0f, Float.valueOf(f2));
        this.h = new ay0(0.5f, 300.0f, Float.valueOf(10.0f * f2));
        Float valueOf = Float.valueOf(0.001f);
        this.i = new ay0(1.0f, 1000.0f, valueOf);
        this.j = new ay0(0.6f, 250.0f, valueOf);
        this.k = new ay0(0.7f, 250.0f, valueOf);
        this.l = dl.a(f, f2);
        this.m = dl.a(0.0f, 5.0f);
        this.n = dl.a(0.0f, 0.001f);
        this.o = dl.a(1.0f, 0.001f);
        this.p = dl.a(1.0f, 0.001f);
        this.q = new nf0();
        this.r = new u41();
        this.s = new tz0(x31.a, null, new d5(1, this), 6);
    }

    public final void a(float f) {
        f31.G(this.a, null, new tk(this, f, null), 3);
    }

    public final float b() {
        return ((Number) this.n.d()).floatValue();
    }

    public final float c() {
        float e = e();
        ie ieVar = this.b;
        return (e - ((Number) ieVar.a()).floatValue()) / (((Number) ieVar.b()).floatValue() - ((Number) ieVar.a()).floatValue());
    }

    public final float d() {
        return ((Number) this.l.e.getValue()).floatValue();
    }

    public final float e() {
        return ((Number) this.l.d()).floatValue();
    }

    public final void f(float f) {
        f31.G(this.a, null, new zk(this, ((Number) n30.m(Float.valueOf(f), this.b)).floatValue(), null), 3);
    }
}

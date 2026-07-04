package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k20 {
    public final hk a;
    public final kv b;
    public final ay0 c;
    public final ay0 d;
    public final y6 e;
    public final y6 f;
    public long g;
    public final h6 h;
    public final cd0 i;
    public final cd0 j;

    public k20(hk hkVar, kv kvVar) {
        h6 h6Var;
        hkVar.getClass();
        kvVar.getClass();
        this.a = hkVar;
        this.b = kvVar;
        this.c = new ay0(0.5f, 300.0f, Float.valueOf(0.001f));
        this.d = new ay0(0.5f, 300.0f, new ch0((Float.floatToRawIntBits(1.0f) << 32) | (Float.floatToRawIntBits(1.0f) & 4294967295L)));
        this.e = dl.a(0.0f, 0.001f);
        this.f = new y6(new ch0(0L), dl.u, new ch0((Float.floatToRawIntBits(1.0f) << 32) | (Float.floatToRawIntBits(1.0f) & 4294967295L)), 8);
        this.g = 0L;
        if (y20.n()) {
            h6Var = g1.a("\nuniform float2 size;\nlayout(color) uniform half4 color;\nuniform float radius;\nuniform float2 position;\n\nhalf4 main(float2 coord) {\n    float dist = distance(coord, position);\n    float intensity = smoothstep(radius, radius * 0.5, dist);\n    return color * intensity;\n}");
        } else {
            h6Var = null;
        }
        this.h = h6Var;
        int i = 2;
        j20 j20Var = new j20(this, i);
        zc0 zc0Var = zc0.a;
        this.i = o4.z(zc0Var, j20Var);
        this.j = uz0.a(zc0Var, hkVar, new d5(i, this));
    }
}

package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kw0 extends bd0 implements r40, qu0 {
    public long A;
    public int B;
    public te C;
    public q2 D;
    public float s;
    public float t;
    public float u;
    public float v;
    public long w;
    public zv0 x;
    public boolean y;
    public long z;

    @Override // defpackage.r40
    public final qc0 Y(ob0 ob0Var, kc0 kc0Var, long j) {
        em0 v = kc0Var.v(j);
        return ob0Var.e0(v.e, v.f, fr.e, new o6(7, v, this));
    }

    @Override // defpackage.qu0
    public final void f0(bv0 bv0Var) {
        if (!this.y) {
            return;
        }
        zv0 zv0Var = this.x;
        t30[] t30VarArr = zu0.a;
        av0 av0Var = wu0.M;
        t30 t30Var = zu0.a[30];
        bv0Var.a(av0Var, zv0Var);
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean h0() {
        return false;
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean i0() {
        return false;
    }

    @Override // defpackage.bd0
    public final boolean q0() {
        return false;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SimpleGraphicsLayerModifier(scaleX=");
        sb.append(this.s);
        sb.append(", scaleY=");
        sb.append(this.t);
        sb.append(", alpha = ");
        sb.append(this.u);
        sb.append(", translationX=0.0, translationY=0.0, shadowElevation=0.0, rotationX=0.0, rotationY=0.0, rotationZ=0.0, cameraDistance=");
        sb.append(this.v);
        sb.append(", transformOrigin=");
        long j = this.w;
        int i = s21.b;
        sb.append((Object) ("TransformOrigin(packedValue=" + j + ')'));
        sb.append(", shape=");
        sb.append(this.x);
        sb.append(", clip=");
        sb.append(this.y);
        sb.append(", renderEffect=null, ambientShadowColor=");
        sb.append((Object) se.j(this.z));
        sb.append(", spotShadowColor=");
        sb.append((Object) se.j(this.A));
        sb.append(", compositingStrategy=CompositingStrategy(value=0), blendMode=");
        sb.append((Object) f31.T(this.B));
        sb.append(", colorFilter=");
        sb.append(this.C);
        sb.append(')');
        return sb.toString();
    }

    @Override // defpackage.qu0
    public final boolean u() {
        return false;
    }
}

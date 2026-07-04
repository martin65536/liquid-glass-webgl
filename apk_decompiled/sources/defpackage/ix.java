package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ix extends gd0 {
    public final float a;
    public final float b;
    public final float c;
    public final long d;
    public final zv0 e;
    public final boolean f;
    public final long g;
    public final long h;
    public final int i;
    public final te j;

    public ix(float f, float f2, float f3, long j, zv0 zv0Var, boolean z, long j2, long j3, int i, te teVar) {
        this.a = f;
        this.b = f2;
        this.c = f3;
        this.d = j;
        this.e = zv0Var;
        this.f = z;
        this.g = j2;
        this.h = j3;
        this.i = i;
        this.j = teVar;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [kw0, java.lang.Object, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        bd0Var.t = this.b;
        bd0Var.u = this.c;
        bd0Var.v = 8.0f;
        bd0Var.w = this.d;
        bd0Var.x = this.e;
        bd0Var.y = this.f;
        bd0Var.z = this.g;
        bd0Var.A = this.h;
        bd0Var.B = this.i;
        bd0Var.C = this.j;
        bd0Var.D = new q2(23, bd0Var);
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof ix) {
                ix ixVar = (ix) obj;
                if (Float.compare(this.a, ixVar.a) == 0 && Float.compare(this.b, ixVar.b) == 0 && Float.compare(this.c, ixVar.c) == 0 && Float.compare(0.0f, 0.0f) == 0 && Float.compare(0.0f, 0.0f) == 0 && Float.compare(0.0f, 0.0f) == 0 && Float.compare(0.0f, 0.0f) == 0 && Float.compare(0.0f, 0.0f) == 0 && Float.compare(0.0f, 0.0f) == 0 && Float.compare(8.0f, 8.0f) == 0) {
                    long j = ixVar.d;
                    int i = s21.b;
                    if (this.d == j && o20.e(this.e, ixVar.e) && this.f == ixVar.f && se.c(this.g, ixVar.g) && se.c(this.h, ixVar.h) && this.i == ixVar.i && o20.e(this.j, ixVar.j)) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        ng0 ng0Var;
        kw0 kw0Var = (kw0) bd0Var;
        kw0Var.s = this.a;
        kw0Var.t = this.b;
        kw0Var.u = this.c;
        kw0Var.v = 8.0f;
        kw0Var.w = this.d;
        kw0Var.x = this.e;
        kw0Var.y = this.f;
        kw0Var.z = this.g;
        kw0Var.A = this.h;
        kw0Var.B = this.i;
        kw0Var.C = this.j;
        q2 q2Var = kw0Var.D;
        if (kw0Var.e.r && (ng0Var = k81.B(kw0Var, 2).t) != null) {
            ng0Var.m1(q2Var, true);
        }
    }

    public final int hashCode() {
        int i;
        int hashCode;
        int s = d3.s(8.0f, d3.s(0.0f, d3.s(0.0f, d3.s(0.0f, d3.s(0.0f, d3.s(0.0f, d3.s(0.0f, d3.s(this.c, d3.s(this.b, Float.floatToIntBits(this.a) * 31, 31), 31), 31), 31), 31), 31), 31), 31), 31);
        int i2 = s21.b;
        long j = this.d;
        int hashCode2 = (this.e.hashCode() + ((s + ((int) (j ^ (j >>> 32)))) * 31)) * 31;
        if (this.f) {
            i = 1231;
        } else {
            i = 1237;
        }
        int i3 = (((se.i(this.h) + ((se.i(this.g) + ((hashCode2 + i) * 961)) * 31)) * 961) + this.i) * 31;
        te teVar = this.j;
        if (teVar == null) {
            hashCode = 0;
        } else {
            hashCode = teVar.hashCode();
        }
        return i3 + hashCode;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("GraphicsLayerElement(scaleX=");
        sb.append(this.a);
        sb.append(", scaleY=");
        sb.append(this.b);
        sb.append(", alpha=");
        sb.append(this.c);
        sb.append(", translationX=0.0, translationY=0.0, shadowElevation=0.0, rotationX=0.0, rotationY=0.0, rotationZ=0.0, cameraDistance=8.0, transformOrigin=");
        int i = s21.b;
        sb.append((Object) ("TransformOrigin(packedValue=" + this.d + ')'));
        sb.append(", shape=");
        sb.append(this.e);
        sb.append(", clip=");
        sb.append(this.f);
        sb.append(", renderEffect=null, ambientShadowColor=");
        sb.append((Object) se.j(this.g));
        sb.append(", spotShadowColor=");
        sb.append((Object) se.j(this.h));
        sb.append(", compositingStrategy=CompositingStrategy(value=0), blendMode=");
        sb.append((Object) f31.T(this.i));
        sb.append(", colorFilter=");
        sb.append(this.j);
        sb.append(')');
        return sb.toString();
    }
}

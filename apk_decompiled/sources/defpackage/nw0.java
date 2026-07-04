package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class nw0 extends gd0 {
    public final float a;
    public final float b;
    public final float c;
    public final float d;
    public final boolean e;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ nw0(float r3, float r4, int r5) {
        /*
            r2 = this;
            r0 = r5 & 2
            r1 = 2143289344(0x7fc00000, float:NaN)
            if (r0 == 0) goto L7
            r3 = r1
        L7:
            r0 = r5 & 4
            if (r0 == 0) goto Ld
            r0 = r1
            goto Lf
        Ld:
            r0 = 1137180672(0x43c80000, float:400.0)
        Lf:
            r5 = r5 & 8
            if (r5 == 0) goto L14
            r4 = r1
        L14:
            r2.<init>(r1, r3, r0, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.nw0.<init>(float, float, int):void");
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [ow0, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        bd0Var.t = this.b;
        bd0Var.u = this.c;
        bd0Var.v = this.d;
        bd0Var.w = this.e;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof nw0) {
                nw0 nw0Var = (nw0) obj;
                if (!eo.a(this.a, nw0Var.a) || !eo.a(this.b, nw0Var.b) || !eo.a(this.c, nw0Var.c) || !eo.a(this.d, nw0Var.d) || this.e != nw0Var.e) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        ow0 ow0Var = (ow0) bd0Var;
        ow0Var.s = this.a;
        ow0Var.t = this.b;
        ow0Var.u = this.c;
        ow0Var.v = this.d;
        ow0Var.w = this.e;
    }

    public final int hashCode() {
        int i;
        int s = d3.s(this.d, d3.s(this.c, d3.s(this.b, Float.floatToIntBits(this.a) * 31, 31), 31), 31);
        if (this.e) {
            i = 1231;
        } else {
            i = 1237;
        }
        return s + i;
    }

    public nw0(float f, float f2, float f3, float f4) {
        this.a = f;
        this.b = f2;
        this.c = f3;
        this.d = f4;
        this.e = true;
    }
}

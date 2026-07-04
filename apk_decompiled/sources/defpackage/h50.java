package defpackage;

import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class h50 implements iz0 {
    public m40 e = m40.f;
    public float f;
    public float g;
    public final /* synthetic */ n50 h;

    public h50(n50 n50Var) {
        this.h = n50Var;
    }

    @Override // defpackage.mm
    public final float B() {
        return this.f;
    }

    @Override // defpackage.rc0
    public final boolean D() {
        v40 v40Var = this.h.e.I.d;
        if (v40Var != v40.h && v40Var != v40.f) {
            return false;
        }
        return true;
    }

    @Override // defpackage.mm
    public final float G(float f) {
        return B() * f;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0081  */
    @Override // defpackage.iz0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.List L(defpackage.kv r11, java.lang.Object r12) {
        /*
            r10 = this;
            n50 r10 = r10.h
            r10.h()
            z40 r0 = r10.e
            d50 r1 = r0.I
            v40 r1 = r1.d
            v40 r2 = defpackage.v40.g
            v40 r3 = defpackage.v40.e
            if (r1 == r3) goto L21
            if (r1 == r2) goto L21
            v40 r4 = defpackage.v40.f
            if (r1 == r4) goto L21
            v40 r4 = defpackage.v40.h
            if (r1 != r4) goto L1c
            goto L21
        L1c:
            java.lang.String r4 = "subcompose can only be used inside the measure or layout blocks"
            defpackage.q00.b(r4)
        L21:
            ve0 r4 = r10.k
            java.lang.Object r5 = r4.g(r12)
            r6 = 0
            r7 = 1
            if (r5 != 0) goto L67
            ve0 r5 = r10.n
            java.lang.Object r5 = r5.k(r12)
            z40 r5 = (defpackage.z40) r5
            if (r5 == 0) goto L4e
            ve0 r8 = r10.j
            java.lang.Object r8 = r8.g(r5)
            f50 r8 = (defpackage.f50) r8
            int r8 = r10.s
            if (r8 <= 0) goto L42
            goto L47
        L42:
            java.lang.String r8 = "Check failed."
            defpackage.q00.b(r8)
        L47:
            int r8 = r10.s
            int r8 = r8 + (-1)
            r10.s = r8
            goto L64
        L4e:
            z40 r5 = r10.n(r12)
            if (r5 != 0) goto L64
            int r5 = r10.h
            z40 r8 = new z40
            r9 = 2
            r8.<init>(r9)
            r0.t = r7
            r0.y(r5, r8)
            r0.t = r6
            r5 = r8
        L64:
            r4.m(r12, r5)
        L67:
            z40 r5 = (defpackage.z40) r5
            java.util.List r4 = r0.n()
            int r8 = r10.h
            if (r8 < 0) goto L7e
            bf0 r4 = (defpackage.bf0) r4
            ef0 r9 = r4.e
            int r9 = r9.g
            if (r8 >= r9) goto L7e
            java.lang.Object r4 = r4.get(r8)
            goto L7f
        L7e:
            r4 = 0
        L7f:
            if (r4 == r5) goto Laf
            java.util.List r0 = r0.n()
            bf0 r0 = (defpackage.bf0) r0
            ef0 r0 = r0.e
            int r0 = r0.i(r5)
            int r4 = r10.h
            if (r0 < r4) goto L92
            goto La8
        L92:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r8 = "Key \""
            r4.<init>(r8)
            r4.append(r12)
            java.lang.String r8 = "\" was already used. If you are using LazyColumn/Row please make sure you provide a unique key for each item."
            r4.append(r8)
            java.lang.String r4 = r4.toString()
            defpackage.q00.a(r4)
        La8:
            int r4 = r10.h
            if (r4 == r0) goto Laf
            r10.j(r0, r4)
        Laf:
            int r0 = r10.h
            int r0 = r0 + r7
            r10.h = r0
            r10.m(r5, r12, r6, r11)
            if (r1 == r3) goto Lc1
            if (r1 != r2) goto Lbc
            goto Lc1
        Lbc:
            java.util.List r10 = r5.l()
            return r10
        Lc1:
            d50 r10 = r5.I
            oc0 r10 = r10.p
            java.util.List r10 = r10.m0()
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.h50.L(kv, java.lang.Object):java.util.List");
    }

    @Override // defpackage.mm
    public final /* synthetic */ float O(long j) {
        return d3.e(this, j);
    }

    @Override // defpackage.mm
    public final /* synthetic */ int S(float f) {
        return d3.c(f, this);
    }

    @Override // defpackage.mm
    public final /* synthetic */ long Z(long j) {
        return d3.h(this, j);
    }

    @Override // defpackage.mm
    public final /* synthetic */ float d0(long j) {
        return d3.g(this, j);
    }

    @Override // defpackage.rc0
    public final qc0 e0(int i, int i2, Map map, gv gvVar) {
        return r(i, i2, map, null, gvVar);
    }

    @Override // defpackage.rc0
    public final m40 getLayoutDirection() {
        return this.e;
    }

    @Override // defpackage.mm
    public final long j0(float f) {
        return d3.i(o0(f), this);
    }

    @Override // defpackage.mm
    public final float o0(float f) {
        return f / B();
    }

    public final qc0 r(int i, int i2, Map map, gv gvVar, gv gvVar2) {
        if ((i & (-16777216)) != 0 || ((-16777216) & i2) != 0) {
            q00.b("Size(" + i + " x " + i2 + ") is out of range. Each dimension must be between 0 and 16777215.");
        }
        return new g50(i, i2, map, gvVar, this, this.h, gvVar2);
    }

    @Override // defpackage.mm
    public final float y() {
        return this.g;
    }
}

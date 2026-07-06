package defpackage;

import android.os.Trace;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pt extends bd0 implements ai, j40, ah0, ed0 {
    public final kv s;
    public boolean t;
    public boolean u;
    public final int v;

    public pt(int i, kv kvVar, int i2) {
        this.s = (i2 & 4) != 0 ? null : kvVar;
        this.v = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v10, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v13 */
    /* JADX WARN: Type inference failed for: r15v14 */
    /* JADX WARN: Type inference failed for: r15v15 */
    /* JADX WARN: Type inference failed for: r15v25 */
    /* JADX WARN: Type inference failed for: r15v26 */
    /* JADX WARN: Type inference failed for: r15v27 */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7, types: [bd0] */
    /* JADX WARN: Type inference failed for: r15v8 */
    /* JADX WARN: Type inference failed for: r15v9, types: [bd0] */
    /* JADX WARN: Type inference failed for: r1v44, types: [java.lang.Object[], java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17, types: [ef0] */
    /* JADX WARN: Type inference failed for: r5v23, types: [java.lang.Object[], java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v27 */
    public final boolean D0() {
        ef0 ef0Var;
        ot otVar;
        lg0 lg0Var;
        lt ltVar;
        boolean z;
        int i;
        ?? r5;
        Boolean bool;
        int i2;
        int i3;
        lg0 lg0Var2;
        int ordinal = dl.I(this).ordinal();
        if (ordinal != 0) {
            if (ordinal != 1) {
                if (ordinal == 2) {
                    return true;
                }
                if (ordinal != 3) {
                    v7.k();
                    return false;
                }
            }
        } else {
            lt ltVar2 = (lt) ((b4) k81.F(this)).getFocusOwner();
            pt f = ltVar2.f();
            ot I0 = I0();
            if (f == this) {
                E0(I0, I0);
                return true;
            }
            if (f != null || ((lt) ((b4) k81.F(this)).getFocusOwner()).a.G()) {
                if (f != null) {
                    ef0Var = new ef0(new pt[16]);
                    if (!f.e.r) {
                        q00.b("visitAncestors called on an unattached node");
                    }
                    bd0 bd0Var = f.e.i;
                    z40 E = k81.E(f);
                    while (E != null) {
                        if ((E.H.f.h & 1024) != 0) {
                            while (bd0Var != null) {
                                if ((bd0Var.g & 1024) != 0) {
                                    bd0 bd0Var2 = bd0Var;
                                    ef0 ef0Var2 = null;
                                    while (bd0Var2 != null) {
                                        if (bd0Var2 instanceof pt) {
                                            ef0Var.b((pt) bd0Var2);
                                        } else if ((bd0Var2.g & 1024) != 0 && (bd0Var2 instanceof jm)) {
                                            int i4 = 0;
                                            for (bd0 bd0Var3 = ((jm) bd0Var2).t; bd0Var3 != null; bd0Var3 = bd0Var3.j) {
                                                if ((bd0Var3.g & 1024) != 0) {
                                                    i4++;
                                                    if (i4 == 1) {
                                                        bd0Var2 = bd0Var3;
                                                    } else {
                                                        if (ef0Var2 == null) {
                                                            ef0Var2 = new ef0(new bd0[16]);
                                                        }
                                                        if (bd0Var2 != null) {
                                                            ef0Var2.b(bd0Var2);
                                                            bd0Var2 = null;
                                                        }
                                                        ef0Var2.b(bd0Var3);
                                                    }
                                                }
                                            }
                                            if (i4 == 1) {
                                            }
                                        }
                                        bd0Var2 = k81.h(ef0Var2);
                                    }
                                }
                                bd0Var = bd0Var.i;
                            }
                        }
                        E = E.s();
                        if (E != null && (lg0Var2 = E.H) != null) {
                            bd0Var = lg0Var2.e;
                        } else {
                            bd0Var = null;
                        }
                    }
                } else {
                    ef0Var = null;
                }
                pt[] ptVarArr = new pt[16];
                pt[] ptVarArr2 = new pt[16];
                if (!this.e.r) {
                    q00.b("visitAncestors called on an unattached node");
                }
                bd0 bd0Var4 = this.e.i;
                z40 E2 = k81.E(this);
                int i5 = 0;
                int i6 = 0;
                boolean z2 = true;
                while (E2 != null) {
                    if ((E2.H.f.h & 1024) != 0) {
                        while (bd0Var4 != null) {
                            if ((bd0Var4.g & 1024) != 0) {
                                pt ptVar = bd0Var4;
                                ef0 ef0Var3 = null;
                                while (ptVar != 0) {
                                    if (ptVar instanceof pt) {
                                        pt ptVar2 = ptVar;
                                        if (ef0Var != null) {
                                            bool = Boolean.valueOf(ef0Var.j(ptVar2));
                                        } else {
                                            bool = null;
                                        }
                                        if (o20.e(bool, Boolean.TRUE)) {
                                            int i7 = i5 + 1;
                                            if (ptVarArr.length < i7) {
                                                int length = ptVarArr.length;
                                                ltVar = ltVar2;
                                                ?? r1 = new Object[Math.max(i7, length * 2)];
                                                i3 = i7;
                                                System.arraycopy(ptVarArr, 0, r1, 0, length);
                                                ptVarArr = r1;
                                            } else {
                                                ltVar = ltVar2;
                                                i3 = i7;
                                            }
                                            ptVarArr[i5] = ptVar2;
                                            i5 = i3;
                                        } else {
                                            ltVar = ltVar2;
                                            int i8 = i6 + 1;
                                            if (ptVarArr2.length < i8) {
                                                int length2 = ptVarArr2.length;
                                                ?? r52 = new Object[Math.max(i8, length2 * 2)];
                                                i2 = i8;
                                                System.arraycopy(ptVarArr2, 0, r52, 0, length2);
                                                ptVarArr2 = r52;
                                            } else {
                                                i2 = i8;
                                            }
                                            ptVarArr2[i6] = ptVar2;
                                            i6 = i2;
                                        }
                                        if (ptVar2 == f) {
                                            z2 = false;
                                        }
                                        z = false;
                                    } else {
                                        ltVar = ltVar2;
                                        z = true;
                                    }
                                    if (z && (ptVar.g & 1024) != 0 && (ptVar instanceof jm)) {
                                        bd0 bd0Var5 = ptVar.t;
                                        int i9 = 0;
                                        ptVar = ptVar;
                                        while (bd0Var5 != null) {
                                            if ((bd0Var5.g & 1024) != 0) {
                                                int i10 = i9 + 1;
                                                if (i10 == 1) {
                                                    ptVar = bd0Var5;
                                                    i = i10;
                                                } else {
                                                    if (ef0Var3 == null) {
                                                        i = i10;
                                                        r5 = new ef0(new bd0[16]);
                                                    } else {
                                                        i = i10;
                                                        r5 = ef0Var3;
                                                    }
                                                    if (ptVar != 0) {
                                                        r5.b(ptVar);
                                                        ptVar = 0;
                                                    }
                                                    r5.b(bd0Var5);
                                                    ef0Var3 = r5;
                                                    ptVar = ptVar;
                                                }
                                                i9 = i;
                                            }
                                            bd0Var5 = bd0Var5.j;
                                            ptVar = ptVar;
                                        }
                                        if (i9 == 1) {
                                            ltVar2 = ltVar;
                                        }
                                    }
                                    ptVar = k81.h(ef0Var3);
                                    ltVar2 = ltVar;
                                }
                            }
                            bd0Var4 = bd0Var4.i;
                            ltVar2 = ltVar2;
                        }
                    }
                    lt ltVar3 = ltVar2;
                    E2 = E2.s();
                    if (E2 != null && (lg0Var = E2.H) != null) {
                        bd0Var4 = lg0Var.e;
                    } else {
                        bd0Var4 = null;
                    }
                    ltVar2 = ltVar3;
                }
                lt ltVar4 = ltVar2;
                if (!z2 || f == null || dl.o(f, false)) {
                    o30.u(this, new n9(6, this));
                    int ordinal2 = I0().ordinal();
                    if (ordinal2 != 0) {
                        if (ordinal2 != 1) {
                            if (ordinal2 != 2) {
                                if (ordinal2 != 3) {
                                    v7.k();
                                    return false;
                                }
                            }
                        }
                        ((lt) ((b4) k81.F(this)).getFocusOwner()).h(this);
                    }
                    ot otVar2 = ot.g;
                    ot otVar3 = ot.e;
                    if (z2 && f != null) {
                        f.E0(otVar3, otVar2);
                    }
                    ot otVar4 = ot.f;
                    if (ef0Var != null) {
                        int i11 = ef0Var.g - 1;
                        Object[] objArr = ef0Var.e;
                        if (i11 < objArr.length) {
                            while (i11 >= 0) {
                                pt ptVar3 = (pt) objArr[i11];
                                if (ltVar4.f() != this) {
                                    break;
                                }
                                ptVar3.E0(otVar4, otVar2);
                                i11--;
                            }
                        }
                    }
                    int i12 = i6 - 1;
                    if (i12 < ptVarArr2.length) {
                        while (i12 >= 0) {
                            pt ptVar4 = ptVarArr2[i12];
                            if (ltVar4.f() != this) {
                                break;
                            }
                            if (ptVar4 == f) {
                                otVar = otVar3;
                            } else {
                                otVar = otVar2;
                            }
                            ptVar4.E0(otVar, otVar4);
                            i12--;
                        }
                    }
                    if (ltVar4.f() == this) {
                        E0(I0, otVar3);
                        if (ltVar4.f() != this) {
                            break;
                        }
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public final void E0(ot otVar, ot otVar2) {
        lg0 lg0Var;
        kv kvVar;
        lt ltVar = (lt) ((b4) k81.F(this)).getFocusOwner();
        pt f = ltVar.f();
        if (!otVar.equals(otVar2) && (kvVar = this.s) != null) {
            kvVar.d(otVar, otVar2);
        }
        bd0 bd0Var = this.e;
        if (!bd0Var.r) {
            q00.b("visitAncestors called on an unattached node");
        }
        bd0 bd0Var2 = this.e;
        z40 E = k81.E(this);
        while (E != null) {
            if ((E.H.f.h & 5120) != 0) {
                while (bd0Var2 != null) {
                    int i = bd0Var2.g;
                    if ((i & 5120) != 0) {
                        if (bd0Var2 == bd0Var || (i & 1024) == 0) {
                            if ((i & 4096) != 0) {
                                bd0 bd0Var3 = bd0Var2;
                                ef0 ef0Var = null;
                                while (bd0Var3 != null) {
                                    if (bd0Var3 instanceof r9) {
                                        r9 r9Var = (r9) bd0Var3;
                                        if (f == ltVar.f()) {
                                            r9Var.E0();
                                            throw null;
                                        }
                                    } else if ((bd0Var3.g & 4096) != 0 && (bd0Var3 instanceof jm)) {
                                        int i2 = 0;
                                        for (bd0 bd0Var4 = ((jm) bd0Var3).t; bd0Var4 != null; bd0Var4 = bd0Var4.j) {
                                            if ((bd0Var4.g & 4096) != 0) {
                                                i2++;
                                                if (i2 == 1) {
                                                    bd0Var3 = bd0Var4;
                                                } else {
                                                    if (ef0Var == null) {
                                                        ef0Var = new ef0(new bd0[16]);
                                                    }
                                                    if (bd0Var3 != null) {
                                                        ef0Var.b(bd0Var3);
                                                        bd0Var3 = null;
                                                    }
                                                    ef0Var.b(bd0Var4);
                                                }
                                            }
                                        }
                                        if (i2 == 1) {
                                        }
                                    }
                                    bd0Var3 = k81.h(ef0Var);
                                }
                            } else {
                                continue;
                            }
                        } else {
                            return;
                        }
                    }
                    bd0Var2 = bd0Var2.i;
                }
            }
            E = E.s();
            if (E != null && (lg0Var = E.H) != null) {
                bd0Var2 = lg0Var.e;
            } else {
                bd0Var2 = null;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [mt, java.lang.Object] */
    public final mt F0() {
        boolean z;
        boolean z2;
        lg0 lg0Var;
        ?? obj = new Object();
        obj.a = true;
        nt ntVar = nt.b;
        obj.b = ntVar;
        obj.c = ntVar;
        obj.d = ntVar;
        obj.e = ntVar;
        obj.f = ntVar;
        obj.g = ntVar;
        obj.h = ntVar;
        obj.i = ntVar;
        obj.j = w3.q;
        obj.k = w3.r;
        obj.l = x1.H;
        int i = this.v;
        if (i == 1) {
            z = true;
        } else if (i == 0) {
            if (((d10) ((f10) ((e10) n20.p(this, fi.m))).a.getValue()).a == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            z = !z2;
        } else if (i == 2) {
            z = false;
        } else {
            v7.o("Unknown Focusability");
            return null;
        }
        obj.a = z;
        bd0 bd0Var = this.e;
        if (!bd0Var.r) {
            q00.b("visitAncestors called on an unattached node");
        }
        bd0 bd0Var2 = this.e;
        z40 E = k81.E(this);
        loop0: while (E != null) {
            if ((E.H.f.h & 3072) != 0) {
                while (bd0Var2 != null) {
                    int i2 = bd0Var2.g;
                    if ((i2 & 3072) != 0) {
                        if (bd0Var2 != bd0Var && (i2 & 1024) != 0) {
                            break loop0;
                        }
                        if ((i2 & 2048) != 0) {
                            ef0 ef0Var = null;
                            bd0 bd0Var3 = bd0Var2;
                            while (bd0Var3 != null) {
                                if (!(bd0Var3 instanceof r9)) {
                                    if ((bd0Var3.g & 2048) != 0 && (bd0Var3 instanceof jm)) {
                                        int i3 = 0;
                                        for (bd0 bd0Var4 = ((jm) bd0Var3).t; bd0Var4 != null; bd0Var4 = bd0Var4.j) {
                                            if ((bd0Var4.g & 2048) != 0) {
                                                i3++;
                                                if (i3 == 1) {
                                                    bd0Var3 = bd0Var4;
                                                } else {
                                                    if (ef0Var == null) {
                                                        ef0Var = new ef0(new bd0[16]);
                                                    }
                                                    if (bd0Var3 != null) {
                                                        ef0Var.b(bd0Var3);
                                                        bd0Var3 = null;
                                                    }
                                                    ef0Var.b(bd0Var4);
                                                }
                                            }
                                        }
                                        if (i3 == 1) {
                                        }
                                    }
                                    bd0Var3 = k81.h(ef0Var);
                                } else {
                                    ad0 ad0Var = ((r9) bd0Var3).s;
                                    q00.b("applyFocusProperties called on wrong node");
                                    d3.z(ad0Var);
                                    throw null;
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                    bd0Var2 = bd0Var2.i;
                }
            }
            E = E.s();
            if (E != null && (lg0Var = E.H) != null) {
                bd0Var2 = lg0Var.e;
            } else {
                bd0Var2 = null;
            }
        }
        return obj;
    }

    public final wo0 G0(l40 l40Var) {
        wo0 wo0Var = F0().l;
        if (wo0Var != x1.H) {
            if (l40Var == null) {
                return wo0Var;
            }
            return wo0Var.e(l40Var.R(k81.D(this), 0L));
        }
        if (l40Var != null) {
            return l40Var.U(k81.D(this), false);
        }
        return t20.c(0L, d20.J(k81.D(this).g));
    }

    public final y50 H0() {
        lg0 lg0Var;
        Object obj;
        if (!this.e.r) {
            q00.b("visitAncestors called on an unattached node");
        }
        bd0 bd0Var = this.e.i;
        z40 E = k81.E(this);
        while (true) {
            if (E == null) {
                break;
            }
            if ((E.H.f.h & 8388640) != 0) {
                while (bd0Var != null) {
                    int i = bd0Var.g;
                    if ((i & 8388640) != 0) {
                        if ((8388608 & i) != 0) {
                            if (!(bd0Var instanceof y50)) {
                                if (bd0Var instanceof jm) {
                                    bd0Var = null;
                                    for (bd0 bd0Var2 = ((jm) bd0Var).t; bd0Var2 != null; bd0Var2 = bd0Var2.j) {
                                        if (bd0Var2 instanceof y50) {
                                            bd0Var = bd0Var2;
                                        }
                                    }
                                } else {
                                    bd0Var = null;
                                }
                            }
                            y50 y50Var = (y50) bd0Var;
                            if (y50Var != null) {
                                return y50Var;
                            }
                        } else if ((i & 32) != 0) {
                            if (bd0Var instanceof ed0) {
                                obj = bd0Var;
                            } else if (bd0Var instanceof jm) {
                                obj = null;
                                for (bd0 bd0Var3 = ((jm) bd0Var).t; bd0Var3 != null; bd0Var3 = bd0Var3.j) {
                                    if (bd0Var3 instanceof ed0) {
                                        obj = bd0Var3;
                                    }
                                }
                            } else {
                                obj = null;
                            }
                            ed0 ed0Var = (ed0) obj;
                            if (ed0Var != null) {
                                ed0Var.v();
                            }
                        }
                    }
                    bd0Var = bd0Var.i;
                }
            }
            E = E.s();
            if (E != null && (lg0Var = E.H) != null) {
                bd0Var = lg0Var.e;
            } else {
                bd0Var = null;
            }
        }
        return null;
    }

    public final ot I0() {
        lg0 lg0Var;
        boolean z = this.r;
        ot otVar = ot.g;
        if (!z) {
            return otVar;
        }
        pt f = ((lt) ((b4) k81.F(this)).getFocusOwner()).f();
        if (f == null) {
            return otVar;
        }
        if (this == f) {
            return ot.e;
        }
        if (f.r) {
            if (!f.e.r) {
                q00.b("visitAncestors called on an unattached node");
            }
            bd0 bd0Var = f.e.i;
            z40 E = k81.E(f);
            while (E != null) {
                if ((E.H.f.h & 1024) != 0) {
                    while (bd0Var != null) {
                        if ((bd0Var.g & 1024) != 0) {
                            bd0 bd0Var2 = bd0Var;
                            ef0 ef0Var = null;
                            while (bd0Var2 != null) {
                                if (bd0Var2 instanceof pt) {
                                    if (this == ((pt) bd0Var2)) {
                                        return ot.f;
                                    }
                                } else if ((bd0Var2.g & 1024) != 0 && (bd0Var2 instanceof jm)) {
                                    int i = 0;
                                    for (bd0 bd0Var3 = ((jm) bd0Var2).t; bd0Var3 != null; bd0Var3 = bd0Var3.j) {
                                        if ((bd0Var3.g & 1024) != 0) {
                                            i++;
                                            if (i == 1) {
                                                bd0Var2 = bd0Var3;
                                            } else {
                                                if (ef0Var == null) {
                                                    ef0Var = new ef0(new bd0[16]);
                                                }
                                                if (bd0Var2 != null) {
                                                    ef0Var.b(bd0Var2);
                                                    bd0Var2 = null;
                                                }
                                                ef0Var.b(bd0Var3);
                                            }
                                        }
                                    }
                                    if (i == 1) {
                                    }
                                }
                                bd0Var2 = k81.h(ef0Var);
                            }
                        }
                        bd0Var = bd0Var.i;
                    }
                }
                E = E.s();
                if (E != null && (lg0Var = E.H) != null) {
                    bd0Var = lg0Var.e;
                } else {
                    bd0Var = null;
                }
            }
        }
        return otVar;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [ep0, java.lang.Object] */
    public final void J0() {
        int ordinal = I0().ordinal();
        int i = 3;
        if (ordinal != 0) {
            if (ordinal != 1) {
                if (ordinal != 2) {
                    if (ordinal != 3) {
                        v7.k();
                        return;
                    }
                    return;
                }
            } else {
                return;
            }
        }
        ?? obj = new Object();
        o30.u(this, new u3(i, obj, this));
        Object obj2 = obj.e;
        if (obj2 != null) {
            if (!((mt) obj2).a) {
                ((lt) ((b4) k81.F(this)).getFocusOwner()).b(8, true, true);
                return;
            }
            return;
        }
        o20.G("focusProperties");
        throw null;
    }

    public final boolean K0(int i) {
        Trace.beginSection("FocusTransactions:requestFocus");
        try {
            if (F0().a) {
                return D0();
            }
            return t20.r(this, i, new oj0(i));
        } finally {
            Trace.endSection();
        }
    }

    @Override // defpackage.ah0
    public final void P() {
        J0();
    }

    @Override // defpackage.bd0
    public final boolean q0() {
        return false;
    }

    @Override // defpackage.ed0
    public final /* synthetic */ x1 v() {
        return x1.F;
    }

    @Override // defpackage.bd0
    public final void v0() {
        int ordinal = I0().ordinal();
        if (ordinal != 0) {
            if (ordinal != 1) {
                if (ordinal != 2) {
                    if (ordinal == 3) {
                        return;
                    }
                    v7.k();
                    return;
                }
            } else {
                ((b4) k81.F(this)).getFocusOwner();
                n20.s(this);
                return;
            }
        }
        lt ltVar = (lt) ((b4) k81.F(this)).getFocusOwner();
        ltVar.b(8, true, false);
        ltVar.d.a();
    }

    @Override // defpackage.bd0
    public final void x0() {
        if (I0().a()) {
            ((lt) ((b4) k81.F(this)).getFocusOwner()).b(8, true, true);
        }
    }

    @Override // defpackage.sc0
    public final /* synthetic */ void C(long j) {
    }

    @Override // defpackage.j40
    public final void x(l40 l40Var) {
    }
}

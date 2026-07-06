package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class jm extends bd0 {
    public final int s = og0.d(this);
    public bd0 t;

    @Override // defpackage.bd0
    public final void A0() {
        super.A0();
        for (bd0 bd0Var = this.t; bd0Var != null; bd0Var = bd0Var.j) {
            bd0Var.A0();
        }
    }

    @Override // defpackage.bd0
    public final void B0(bd0 bd0Var) {
        this.e = bd0Var;
        for (bd0 bd0Var2 = this.t; bd0Var2 != null; bd0Var2 = bd0Var2.j) {
            bd0Var2.B0(bd0Var);
        }
    }

    @Override // defpackage.bd0
    public final void C0(ng0 ng0Var) {
        this.l = ng0Var;
        for (bd0 bd0Var = this.t; bd0Var != null; bd0Var = bd0Var.j) {
            bd0Var.C0(ng0Var);
        }
    }

    public final im D0(im imVar) {
        bd0 bd0Var;
        bd0 bd0Var2;
        bd0 bd0Var3 = ((bd0) imVar).e;
        if (bd0Var3 != imVar) {
            if (imVar instanceof bd0) {
                bd0Var = (bd0) imVar;
            } else {
                bd0Var = null;
            }
            if (bd0Var != null) {
                bd0Var2 = bd0Var.i;
            } else {
                bd0Var2 = null;
            }
            if (bd0Var3 != this.e || !o20.e(bd0Var2, this)) {
                v7.o("Cannot delegate to an already delegated node");
                return null;
            }
        } else {
            if (bd0Var3.r) {
                q00.b("Cannot delegate to an already attached node");
            }
            bd0Var3.B0(this.e);
            int i = this.g;
            int e = og0.e(bd0Var3);
            bd0Var3.g = e;
            int i2 = this.g;
            int i3 = e & 2;
            if (i3 != 0 && (i2 & 2) != 0 && !(this instanceof r40)) {
                q00.b("Delegating to multiple LayoutModifierNodes without the delegating node implementing LayoutModifierNode itself is not allowed.\nDelegating Node: " + this + "\nDelegate Node: " + bd0Var3);
            }
            bd0Var3.j = this.t;
            this.t = bd0Var3;
            bd0Var3.i = this;
            F0(e | this.g, false);
            if (this.r) {
                if (i3 != 0 && (i & 2) == 0) {
                    lg0 lg0Var = k81.E(this).H;
                    this.e.C0(null);
                    lg0Var.g();
                } else {
                    C0(this.l);
                }
                bd0Var3.r0();
                bd0Var3.z0();
                if (!bd0Var3.r) {
                    q00.b("autoInvalidateInsertedNode called on unattached node");
                }
                og0.a(bd0Var3, -1, 1);
            }
        }
        return imVar;
    }

    public final void E0(im imVar) {
        bd0 bd0Var = null;
        for (bd0 bd0Var2 = this.t; bd0Var2 != null; bd0Var2 = bd0Var2.j) {
            if (bd0Var2 == imVar) {
                boolean z = bd0Var2.r;
                if (z) {
                    oe0 oe0Var = og0.a;
                    if (!z) {
                        q00.b("autoInvalidateRemovedNode called on unattached node");
                    }
                    og0.a(bd0Var2, -1, 2);
                    bd0Var2.A0();
                    bd0Var2.s0();
                }
                bd0Var2.B0(bd0Var2);
                bd0Var2.h = 0;
                bd0 bd0Var3 = bd0Var2.j;
                if (bd0Var == null) {
                    this.t = bd0Var3;
                } else {
                    bd0Var.j = bd0Var3;
                }
                bd0Var2.j = null;
                bd0Var2.i = null;
                int i = this.g;
                int e = og0.e(this);
                F0(e, true);
                if (this.r && (i & 2) != 0 && (e & 2) == 0) {
                    lg0 lg0Var = k81.E(this).H;
                    this.e.C0(null);
                    lg0Var.g();
                    return;
                }
                return;
            }
            bd0Var = bd0Var2;
        }
        v7.e(imVar, "Could not find delegate: ");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [bd0] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    public final void F0(int i, boolean z) {
        int i2;
        bd0 bd0Var;
        int i3 = this.g;
        this.g = i;
        if (i3 != i) {
            bd0 bd0Var2 = this.e;
            if (bd0Var2 == this) {
                this.h = i;
            }
            boolean z2 = this.r;
            ?? r2 = this;
            if (z2) {
                while (r2 != 0) {
                    i |= r2.g;
                    r2.g = i;
                    if (r2 == bd0Var2) {
                        break;
                    } else {
                        r2 = r2.i;
                    }
                }
                if (z && r2 == bd0Var2) {
                    i = og0.e(bd0Var2);
                    bd0Var2.g = i;
                }
                if (r2 != 0 && (bd0Var = r2.j) != null) {
                    i2 = bd0Var.h;
                } else {
                    i2 = 0;
                }
                int i4 = i | i2;
                for (bd0 bd0Var3 = r2; bd0Var3 != null; bd0Var3 = bd0Var3.i) {
                    i4 |= bd0Var3.g;
                    bd0Var3.h = i4;
                }
            }
        }
    }

    @Override // defpackage.bd0
    public final void r0() {
        super.r0();
        for (bd0 bd0Var = this.t; bd0Var != null; bd0Var = bd0Var.j) {
            bd0Var.C0(this.l);
            if (!bd0Var.r) {
                bd0Var.r0();
            }
        }
    }

    @Override // defpackage.bd0
    public final void s0() {
        for (bd0 bd0Var = this.t; bd0Var != null; bd0Var = bd0Var.j) {
            bd0Var.s0();
        }
        super.s0();
    }

    @Override // defpackage.bd0
    public final void y0() {
        super.y0();
        for (bd0 bd0Var = this.t; bd0Var != null; bd0Var = bd0Var.j) {
            bd0Var.y0();
        }
    }

    @Override // defpackage.bd0
    public final void z0() {
        for (bd0 bd0Var = this.t; bd0Var != null; bd0Var = bd0Var.j) {
            bd0Var.z0();
        }
        super.z0();
    }
}

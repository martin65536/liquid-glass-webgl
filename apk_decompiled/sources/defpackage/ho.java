package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ho extends bd0 implements w21, j40 {
    public ho s;
    public ho t;
    public long u;

    @Override // defpackage.sc0
    public final void C(long j) {
        this.u = j;
    }

    public final boolean D0() {
        ho hoVar = this.s;
        if (hoVar == null) {
            ho hoVar2 = this.t;
            if (hoVar2 != null) {
                return hoVar2.D0();
            }
            return false;
        }
        return hoVar.D0();
    }

    public final void E0() {
        ho hoVar = this.t;
        if (hoVar == null) {
            ho hoVar2 = this.s;
            if (hoVar2 != null) {
                hoVar2.E0();
                return;
            }
            return;
        }
        hoVar.E0();
    }

    public final void F0() {
        ho hoVar = this.t;
        if (hoVar != null) {
            hoVar.F0();
        }
        ho hoVar2 = this.s;
        if (hoVar2 != null) {
            hoVar2.F0();
        }
        this.s = null;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [ep0, java.lang.Object] */
    public final void G0(j2 j2Var) {
        w21 w21Var;
        ho hoVar;
        ho hoVar2 = this.s;
        int i = 1;
        if (hoVar2 != null && o4.h(hoVar2, dl.x(j2Var))) {
            hoVar = hoVar2;
        } else {
            if (!this.e.r) {
                w21Var = null;
            } else {
                ?? obj = new Object();
                d20.M(this, new oj(obj, this, j2Var, i));
                w21Var = (w21) obj.e;
            }
            hoVar = (ho) w21Var;
        }
        if (hoVar != null && hoVar2 == null) {
            hoVar.E0();
            hoVar.G0(j2Var);
            ho hoVar3 = this.t;
            if (hoVar3 != null) {
                hoVar3.F0();
            }
        } else if (hoVar == null && hoVar2 != null) {
            ho hoVar4 = this.t;
            if (hoVar4 != null) {
                hoVar4.E0();
                hoVar4.G0(j2Var);
            }
            hoVar2.F0();
        } else if (!o20.e(hoVar, hoVar2)) {
            if (hoVar != null) {
                hoVar.E0();
                hoVar.G0(j2Var);
            }
            if (hoVar2 != null) {
                hoVar2.F0();
            }
        } else if (hoVar != null) {
            hoVar.G0(j2Var);
        } else {
            ho hoVar5 = this.t;
            if (hoVar5 != null) {
                hoVar5.G0(j2Var);
            }
        }
        this.s = hoVar;
    }

    public final void H0() {
        ho hoVar = this.t;
        if (hoVar == null) {
            ho hoVar2 = this.s;
            if (hoVar2 != null) {
                hoVar2.H0();
                return;
            }
            return;
        }
        hoVar.H0();
    }

    @Override // defpackage.bd0
    public final void v0() {
        this.t = null;
        this.s = null;
    }

    @Override // defpackage.w21
    public final Object z() {
        return x1.D;
    }

    @Override // defpackage.j40
    public final /* synthetic */ void x(l40 l40Var) {
    }
}

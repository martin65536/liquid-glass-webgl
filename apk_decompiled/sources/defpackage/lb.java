package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lb extends bd0 implements hb, j40 {
    public cj s;
    public boolean t;

    public static final wo0 D0(lb lbVar, ng0 ng0Var, u3 u3Var) {
        wo0 wo0Var;
        if (lbVar.r && lbVar.t) {
            ng0 D = k81.D(lbVar);
            if (!ng0Var.P0().r) {
                ng0Var = null;
            }
            if (ng0Var != null && (wo0Var = (wo0) u3Var.a()) != null) {
                float f = D.U(ng0Var, false).a;
                return wo0Var.e((Float.floatToRawIntBits(r4.b) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
            }
        }
        return null;
    }

    @Override // defpackage.hb
    public final Object k0(ng0 ng0Var, u3 u3Var, sz0 sz0Var) {
        Object q = dl.q(new kb(this, ng0Var, u3Var, new y8(this, ng0Var, u3Var, 1), null), sz0Var);
        if (q == ik.e) {
            return q;
        }
        return x31.a;
    }

    @Override // defpackage.bd0
    public final boolean q0() {
        return false;
    }

    @Override // defpackage.j40
    public final void x(l40 l40Var) {
        this.t = true;
    }

    @Override // defpackage.sc0
    public final /* synthetic */ void C(long j) {
    }
}

package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class bd0 implements im {
    public hj f;
    public int g;
    public bd0 i;
    public bd0 j;
    public bh0 k;
    public ng0 l;
    public boolean m;
    public boolean n;
    public boolean o;
    public boolean p;
    public u3 q;
    public boolean r;
    public bd0 e = this;
    public int h = -1;

    public void A0() {
        if (!this.r) {
            q00.b("node detached multiple times");
        }
        if (this.l == null) {
            q00.b("detach invoked on a node without a coordinator");
        }
        if (!this.p) {
            q00.b("Must run runDetachLifecycle() once after runAttachLifecycle() and before markAsDetached()");
        }
        this.p = false;
        u3 u3Var = this.q;
        if (u3Var != null) {
            u3Var.a();
        }
        v0();
    }

    public void B0(bd0 bd0Var) {
        this.e = bd0Var;
    }

    public void C0(ng0 ng0Var) {
        this.l = ng0Var;
    }

    public final hk p0() {
        hj hjVar = this.f;
        if (hjVar == null) {
            hj d = dl.d(((b4) k81.F(this)).getCoroutineContext().i(new f30((d30) ((b4) k81.F(this)).getCoroutineContext().j(x1.L))));
            this.f = d;
            return d;
        }
        return hjVar;
    }

    public boolean q0() {
        return !(this instanceof q9);
    }

    public void r0() {
        if (this.r) {
            q00.b("node attached multiple times");
        }
        if (this.l == null) {
            q00.b("attach invoked on a node without a coordinator");
        }
        this.r = true;
        this.o = true;
    }

    public void s0() {
        if (!this.r) {
            q00.b("Cannot detach a node that is not attached");
        }
        if (this.o) {
            q00.b("Must run runAttachLifecycle() before markAsDetached()");
        }
        if (this.p) {
            q00.b("Must run runDetachLifecycle() before markAsDetached()");
        }
        this.r = false;
        hj hjVar = this.f;
        if (hjVar != null) {
            lm0 lm0Var = new lm0("The Modifier.Node was detached", 2);
            d30 d30Var = (d30) hjVar.e.j(x1.L);
            if (d30Var != null) {
                d30Var.a(lm0Var);
                this.f = null;
            } else {
                v7.e(hjVar, "Scope cannot be cancelled because it does not have a job: ");
            }
        }
    }

    public void y0() {
        if (!this.r) {
            q00.b("reset() called on an unattached node");
        }
        x0();
    }

    public void z0() {
        if (!this.r) {
            q00.b("Must run markAsAttached() prior to runAttachLifecycle");
        }
        if (!this.o) {
            q00.b("Must run runAttachLifecycle() only once after markAsAttached()");
        }
        this.o = false;
        t0();
        this.p = true;
    }

    public void t0() {
    }

    public /* synthetic */ void u0() {
    }

    public void v0() {
    }

    public /* synthetic */ void w0() {
    }

    public void x0() {
    }
}

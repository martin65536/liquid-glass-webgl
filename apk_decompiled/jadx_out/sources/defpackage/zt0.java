package defpackage;

import android.os.Build;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.widget.EdgeEffect;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zt0 extends zo implements x30, qu0 {
    public e5 N;
    public rl O;
    public final e3 P;
    public final rl Q;
    public final hu0 R;
    public final vt0 S;
    public final pt T;
    public final cj U;
    public wa V;
    public yt0 W;
    public ud0 X;
    public p21 Y;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v5, types: [im, lb, bd0] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.Object, e3] */
    public zt0(e5 e5Var, rl rlVar, je0 je0Var, dj0 dj0Var, au0 au0Var, boolean z, boolean z2) {
        super(n20.n, z, je0Var, dj0Var);
        rl rlVar2;
        this.N = e5Var;
        this.O = rlVar;
        ?? obj = new Object();
        obj.c = new n9(12, obj);
        this.P = obj;
        rl rlVar3 = new rl(new fl(new j2(n20.q)));
        this.Q = rlVar3;
        e5 e5Var2 = this.N;
        rl rlVar4 = this.O;
        if (rlVar4 == null) {
            rlVar2 = rlVar3;
        } else {
            rlVar2 = rlVar4;
        }
        hu0 hu0Var = new hu0(au0Var, e5Var2, rlVar2, dj0Var, z2, obj, this, new wt0(this, 0));
        this.R = hu0Var;
        vt0 vt0Var = new vt0(hu0Var, z);
        this.S = vt0Var;
        pt ptVar = new pt(2, null, 10);
        D0(ptVar);
        this.T = ptVar;
        cj cjVar = new cj(dj0Var, hu0Var, z2, new wt0(this, 1));
        D0(cjVar);
        this.U = cjVar;
        D0(new gg0(vt0Var, obj));
        ?? bd0Var = new bd0();
        bd0Var.s = cjVar;
        D0(bd0Var);
    }

    @Override // defpackage.zo
    public final Object K0(yo yoVar, yo yoVar2) {
        hu0 hu0Var = this.R;
        Object f = hu0Var.f(gf0.f, new f(yoVar, hu0Var, null, 13), yoVar2);
        if (f == ik.e) {
            return f;
        }
        return x31.a;
    }

    @Override // defpackage.zo, defpackage.xm0
    public final void N(pm0 pm0Var, qm0 qm0Var, long j) {
        int i;
        List list = pm0Var.a;
        int size = list.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            if (((Boolean) this.v.e(new an0(((um0) list.get(i2)).i))).booleanValue()) {
                super.N(pm0Var, qm0Var, j);
                break;
            }
            i2++;
        }
        if (this.y == null) {
            kw kwVar = new kw(this);
            D0(kwVar);
            this.y = kwVar;
        }
        if (this.w) {
            qm0 qm0Var2 = qm0.e;
            ij ijVar = null;
            hu0 hu0Var = this.R;
            if (qm0Var == qm0Var2 && pm0Var.d == 6) {
                if (this.X == null) {
                    this.X = new ud0(hu0Var, new j2(1, ViewConfiguration.get(o4.Y(this).getContext())), new fg(2, this, zt0.class, "onWheelScrollStopped", "onWheelScrollStopped-TH1AsA0(J)V", 4, 1), k81.E(this).A);
                }
                ud0 ud0Var = this.X;
                if (ud0Var != null) {
                    hk p0 = p0();
                    if (ud0Var.h == null) {
                        ud0Var.h = f31.G(p0, null, new d(ud0Var, ijVar, 13), 3);
                    }
                }
            }
            ud0 ud0Var2 = this.X;
            qm0 qm0Var3 = qm0.f;
            if (ud0Var2 != null && pm0Var.d == 6) {
                int size2 = list.size();
                int i3 = 0;
                while (true) {
                    if (i3 < size2) {
                        if (((um0) list.get(i3)).b()) {
                            break;
                        } else {
                            i3++;
                        }
                    } else {
                        if (qm0Var == qm0Var2 && ud0Var2.d) {
                            ud0Var2.f(pm0Var);
                            ug0.a(pm0Var);
                        }
                        if (qm0Var == qm0Var3 && !ud0Var2.d && ud0Var2.f(pm0Var)) {
                            ug0.a(pm0Var);
                        }
                    }
                }
            }
            if (qm0Var == qm0Var2 && ((i = pm0Var.d) == 10 || i == 11 || i == 12)) {
                if (this.Y == null) {
                    this.Y = new p21(hu0Var, new fg(2, this, zt0.class, "onTrackpadScrollStopped", "onTrackpadScrollStopped-TH1AsA0(J)V", 4, 2), k81.E(this).A);
                }
                p21 p21Var = this.Y;
                if (p21Var != null) {
                    hk p02 = p0();
                    if (p21Var.g == null) {
                        p21Var.g = f31.G(p02, null, new bh(p21Var, null), 3);
                    }
                }
            }
            p21 p21Var2 = this.Y;
            if (p21Var2 != null) {
                int i4 = pm0Var.d;
                if (i4 == 10 || i4 == 11 || i4 == 12) {
                    int size3 = list.size();
                    for (int i5 = 0; i5 < size3; i5++) {
                        if (((um0) list.get(i5)).b()) {
                            return;
                        }
                    }
                    if (qm0Var == qm0Var2 && p21Var2.d) {
                        p21Var2.d(pm0Var);
                        ug0.a(pm0Var);
                    }
                    if (qm0Var == qm0Var3 && !p21Var2.d && p21Var2.d(pm0Var)) {
                        ug0.a(pm0Var);
                    }
                }
            }
        }
    }

    @Override // defpackage.zo
    public final void Q0(qo qoVar) {
        f31.G(this.P.m(), null, new d(qoVar, this, null, 18), 3);
    }

    @Override // defpackage.x30
    public final boolean U(KeyEvent keyEvent) {
        float f;
        long floatToRawIntBits;
        float f2;
        boolean z = false;
        if (!this.w || ((!v30.a(y20.a(keyEvent.getKeyCode()), v30.n) && !v30.a(y20.a(keyEvent.getKeyCode()), v30.m)) || t20.u(keyEvent) != 2 || keyEvent.isCtrlPressed())) {
            return false;
        }
        if (this.R.d == dj0.e) {
            z = true;
        }
        cj cjVar = this.U;
        if (z) {
            int E0 = (int) (cjVar.E0() & 4294967295L);
            if (v30.a(y20.a(keyEvent.getKeyCode()), v30.m)) {
                f2 = E0;
            } else {
                f2 = -E0;
            }
            floatToRawIntBits = (Float.floatToRawIntBits(0.0f) << 32) | (4294967295L & Float.floatToRawIntBits(f2));
        } else {
            int E02 = (int) (cjVar.E0() >> 32);
            if (v30.a(y20.a(keyEvent.getKeyCode()), v30.m)) {
                f = E02;
            } else {
                f = -E02;
            }
            floatToRawIntBits = (Float.floatToRawIntBits(0.0f) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        }
        f31.G(p0(), null, new yt0(this, floatToRawIntBits, null, 0), 3);
        return true;
    }

    @Override // defpackage.zo
    public final boolean V0() {
        float f;
        float f2;
        float f3;
        float f4;
        hu0 hu0Var = this.R;
        if (!hu0Var.a.b()) {
            e5 e5Var = hu0Var.b;
            if (e5Var != null) {
                iq iqVar = e5Var.c;
                EdgeEffect edgeEffect = iqVar.d;
                if (edgeEffect != null) {
                    if (Build.VERSION.SDK_INT >= 31) {
                        f4 = p7.c(edgeEffect);
                    } else {
                        f4 = 0.0f;
                    }
                    if (f4 != 0.0f) {
                        return true;
                    }
                }
                EdgeEffect edgeEffect2 = iqVar.e;
                if (edgeEffect2 != null) {
                    if (Build.VERSION.SDK_INT >= 31) {
                        f3 = p7.c(edgeEffect2);
                    } else {
                        f3 = 0.0f;
                    }
                    if (f3 != 0.0f) {
                        return true;
                    }
                }
                EdgeEffect edgeEffect3 = iqVar.f;
                if (edgeEffect3 != null) {
                    if (Build.VERSION.SDK_INT >= 31) {
                        f2 = p7.c(edgeEffect3);
                    } else {
                        f2 = 0.0f;
                    }
                    if (f2 != 0.0f) {
                        return true;
                    }
                }
                EdgeEffect edgeEffect4 = iqVar.g;
                if (edgeEffect4 != null) {
                    if (Build.VERSION.SDK_INT >= 31) {
                        f = p7.c(edgeEffect4);
                    } else {
                        f = 0.0f;
                    }
                    if (f == 0.0f) {
                        return false;
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final void Y0(e5 e5Var, rl rlVar, je0 je0Var, dj0 dj0Var, au0 au0Var, boolean z, boolean z2) {
        boolean z3;
        rl rlVar2;
        boolean z4 = true;
        boolean z5 = false;
        if (this.w != z) {
            this.S.b = z;
            z3 = true;
        } else {
            z3 = false;
        }
        if (rlVar == null) {
            rlVar2 = this.Q;
        } else {
            rlVar2 = rlVar;
        }
        hu0 hu0Var = this.R;
        if (!o20.e(hu0Var.a, au0Var)) {
            hu0Var.a = au0Var;
            z5 = true;
        }
        hu0Var.b = e5Var;
        if (hu0Var.d != dj0Var) {
            hu0Var.d = dj0Var;
            z5 = true;
        }
        if (hu0Var.e != z2) {
            hu0Var.e = z2;
        } else {
            z4 = z5;
        }
        hu0Var.c = rlVar2;
        hu0Var.f = this.P;
        cj cjVar = this.U;
        cjVar.s = dj0Var;
        cjVar.u = z2;
        this.N = e5Var;
        this.O = rlVar;
        ts0 ts0Var = n20.n;
        dj0 dj0Var2 = hu0Var.d;
        dj0 dj0Var3 = dj0.e;
        if (dj0Var2 != dj0Var3) {
            dj0Var3 = dj0.f;
        }
        X0(ts0Var, z, je0Var, dj0Var3, z4);
        if (z3) {
            this.V = null;
            this.W = null;
            m20.w(this);
        }
    }

    @Override // defpackage.qu0
    public final void f0(bv0 bv0Var) {
        if (this.w && (this.V == null || this.W == null)) {
            this.V = new wa(8, this);
            this.W = new yt0(this, null);
        }
        wa waVar = this.V;
        if (waVar != null) {
            t30[] t30VarArr = zu0.a;
            bv0Var.a(mu0.d, new n0(null, waVar));
        }
        yt0 yt0Var = this.W;
        if (yt0Var != null) {
            t30[] t30VarArr2 = zu0.a;
            bv0Var.a(mu0.e, yt0Var);
        }
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

    @Override // defpackage.bd0
    public final void t0() {
        if (this.r) {
            mm mmVar = k81.E(this).A;
            rl rlVar = this.Q;
            rlVar.getClass();
            rlVar.a = new fl(new j2(mmVar));
        }
        ud0 ud0Var = this.X;
        if (ud0Var != null) {
            ud0Var.c = k81.E(this).A;
        }
        p21 p21Var = this.Y;
        if (p21Var != null) {
            p21Var.c = k81.E(this).A;
        }
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean u() {
        return true;
    }

    @Override // defpackage.zo, defpackage.bd0
    public final void u0() {
        g0();
        if (this.r) {
            mm mmVar = k81.E(this).A;
            rl rlVar = this.Q;
            rlVar.getClass();
            rlVar.a = new fl(new j2(mmVar));
        }
        ud0 ud0Var = this.X;
        if (ud0Var != null) {
            ud0Var.c = k81.E(this).A;
        }
        p21 p21Var = this.Y;
        if (p21Var != null) {
            p21Var.c = k81.E(this).A;
        }
    }

    @Override // defpackage.zo
    public final void P0(long j) {
    }
}

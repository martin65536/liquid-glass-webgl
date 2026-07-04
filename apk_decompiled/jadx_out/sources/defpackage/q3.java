package defpackage;

import android.graphics.Rect;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class q3 extends bd0 implements hb, qu0, x30, r40, w21 {
    public final q2 s = new q2(3, this);
    public final /* synthetic */ b4 t;

    public q3(b4 b4Var) {
        this.t = b4Var;
    }

    @Override // defpackage.x30
    public final boolean U(KeyEvent keyEvent) {
        bt btVar;
        int i;
        boolean z;
        int[] iArr = et.a;
        long a = y20.a(keyEvent.getKeyCode());
        Integer num = null;
        int i2 = 2;
        if (v30.a(a, v30.b)) {
            btVar = new bt(2);
        } else if (v30.a(a, v30.c)) {
            btVar = new bt(1);
        } else if (v30.a(a, v30.i)) {
            if (keyEvent.isShiftPressed()) {
                i = 2;
            } else {
                i = 1;
            }
            btVar = new bt(i);
        } else if (v30.a(a, v30.g)) {
            btVar = new bt(4);
        } else if (v30.a(a, v30.f)) {
            btVar = new bt(3);
        } else if (!v30.a(a, v30.d) && !v30.a(a, v30.m)) {
            if (!v30.a(a, v30.e) && !v30.a(a, v30.n)) {
                if (!v30.a(a, v30.h) && !v30.a(a, v30.k) && !v30.a(a, v30.o)) {
                    if (!v30.a(a, v30.a) && !v30.a(a, v30.l)) {
                        btVar = null;
                    } else {
                        btVar = new bt(8);
                    }
                } else {
                    btVar = new bt(7);
                }
            } else {
                btVar = new bt(6);
            }
        } else {
            btVar = new bt(5);
        }
        if (btVar != null) {
            int i3 = btVar.a;
            if (t20.u(keyEvent) == 2) {
                b4 b4Var = this.t;
                ((lt) b4Var.getFocusOwner()).getClass();
                Boolean e = ((lt) b4Var.getFocusOwner()).e(i3, b4Var.getEmbeddedViewFocusRect(), new q2(2, btVar));
                if (e != null) {
                    z = e.booleanValue();
                } else {
                    z = true;
                }
                if (z) {
                    return true;
                }
                if (i3 == 1 || i3 == 2) {
                    if (i3 == 5) {
                        num = 33;
                    } else if (i3 == 6) {
                        num = 130;
                    } else if (i3 == 3) {
                        num = 17;
                    } else if (i3 == 4) {
                        num = 66;
                    } else if (i3 == 1) {
                        num = 2;
                    } else if (i3 == 2) {
                        num = 1;
                    }
                    if (num != null) {
                        i2 = num.intValue();
                    }
                    FocusFinder focusFinder = FocusFinder.getInstance();
                    View rootView = b4Var.getRootView();
                    rootView.getClass();
                    View findNextFocus = focusFinder.findNextFocus((ViewGroup) rootView, b4Var.getView(), i2);
                    if (findNextFocus == null || findNextFocus.equals(b4Var)) {
                        return ((lt) b4Var.getFocusOwner()).g(i3);
                    }
                }
            }
        }
        return false;
    }

    @Override // defpackage.r40
    public final qc0 Y(ob0 ob0Var, kc0 kc0Var, long j) {
        em0 v = kc0Var.v(j);
        return ob0Var.B0(v.e, v.f, fr.e, this.s, new p3(v, 0));
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean h0() {
        return false;
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean i0() {
        return false;
    }

    @Override // defpackage.hb
    public final Object k0(ng0 ng0Var, u3 u3Var, sz0 sz0Var) {
        wo0 wo0Var;
        long Y0 = ng0Var.Y0(0L);
        wo0 wo0Var2 = (wo0) u3Var.a();
        if (wo0Var2 != null) {
            wo0Var = wo0Var2.e(Y0);
        } else {
            wo0Var = null;
        }
        if (wo0Var != null) {
            this.t.requestRectangleOnScreen(new Rect((int) wo0Var.a, (int) wo0Var.b, (int) wo0Var.c, (int) wo0Var.d), false);
        }
        return x31.a;
    }

    @Override // defpackage.qu0
    public final /* synthetic */ boolean u() {
        return true;
    }

    @Override // defpackage.w21
    public final Object z() {
        return "androidx.compose.ui.layout.WindowInsetsRulers";
    }

    @Override // defpackage.qu0
    public final void f0(bv0 bv0Var) {
    }
}

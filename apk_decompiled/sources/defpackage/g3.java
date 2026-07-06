package defpackage;

import android.graphics.Rect;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class g3 extends t8 implements gt {
    public final j2 e;
    public final vu0 f;
    public final b4 g;
    public final yo0 h;
    public final String i;
    public final AutofillId j;
    public final ie0 k;
    public boolean l;

    public g3(j2 j2Var, vu0 vu0Var, b4 b4Var, yo0 yo0Var, String str) {
        AutofillId autofillId;
        this.e = j2Var;
        this.f = vu0Var;
        this.g = b4Var;
        this.h = yo0Var;
        this.i = str;
        new Rect();
        b4Var.setImportantForAutofill(1);
        j1 t = m20.t(b4Var);
        if (t != null) {
            autofillId = z0.e(t.a);
        } else {
            autofillId = null;
        }
        if (autofillId != null) {
            this.j = autofillId;
            this.k = new ie0();
            return;
        }
        throw d3.t("Required value was null.");
    }

    @Override // defpackage.gt
    public final void g(pt ptVar, pt ptVar2) {
        z40 E;
        nu0 u;
        z40 E2;
        nu0 u2;
        if (ptVar != null && (E2 = k81.E(ptVar)) != null && (u2 = E2.u()) != null) {
            ve0 ve0Var = u2.e;
            if (ve0Var.b(mu0.g) || ve0Var.b(mu0.h)) {
                ((AutofillManager) this.e.f).notifyViewExited(this.g, E2.f);
            }
        }
        if (ptVar2 != null && (E = k81.E(ptVar2)) != null && (u = E.u()) != null) {
            ve0 ve0Var2 = u.e;
            if (!ve0Var2.b(mu0.g) && !ve0Var2.b(mu0.h)) {
                return;
            }
            int i = E.f;
            this.h.b.h(i, new f3(this, i));
        }
    }
}

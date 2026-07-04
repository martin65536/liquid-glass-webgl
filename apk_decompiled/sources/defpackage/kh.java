package defpackage;

import android.view.View;
import com.kyant.backdrop.catalog.MainActivity;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kh extends p {
    public final ik0 n;
    public boolean o;

    public kh(MainActivity mainActivity) {
        super(mainActivity, null, 0);
        setClipChildren(false);
        setClipToPadding(false);
        int i = 1;
        setImportantForAccessibility(1);
        m5 m5Var = new m5(i, this);
        addOnAttachStateChangeListener(m5Var);
        v7 v7Var = new v7(26);
        o30.s(this).a.add(v7Var);
        this.j = new r90(this, m5Var, v7Var, i);
        this.n = n30.B(null);
    }

    @Override // defpackage.p
    public final void a(bw bwVar, int i) {
        int i2;
        boolean z;
        bwVar.W(420213850);
        if (bwVar.h(this)) {
            i2 = 4;
        } else {
            i2 = 2;
        }
        int i3 = i2 | i;
        if ((i3 & 3) != 2) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i3 & 1, z)) {
            kv kvVar = (kv) this.n.getValue();
            if (kvVar == null) {
                bwVar.V(-1238823553);
            } else {
                bwVar.V(98585282);
                kvVar.d(bwVar, 0);
            }
            bwVar.p(false);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new o(i, 3, this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return kh.class.getName();
    }

    @Override // defpackage.p
    public boolean getShouldCreateCompositionOnAttachedToWindow() {
        return this.o;
    }

    public final void setContent(kv kvVar) {
        nh nhVar;
        View view;
        this.o = true;
        this.n.setValue(kvVar);
        if (!isAttachedToWindow() && getComposeViewContext$ui() == null) {
            return;
        }
        if (this.h == null && !isAttachedToWindow() && ((nhVar = this.i) == null || (view = nhVar.a) == null || !view.isAttachedToWindow())) {
            v7.o("createComposition requires a previous call to createComposition(ComposeViewContext), a parent reference, or the View to be attached to a window. Attach the View or call setParentCompositionReference.");
        } else {
            e();
        }
    }

    public static /* synthetic */ void getShouldCreateCompositionOnAttachedToWindow$annotations() {
    }
}

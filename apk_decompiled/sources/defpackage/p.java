package defpackage;

import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Trace;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import com.kyant.backdrop.catalog.R;
import java.lang.ref.WeakReference;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class p extends ViewGroup {
    public WeakReference e;
    public IBinder f;
    public f81 g;
    public th h;
    public nh i;
    public r90 j;
    public boolean k;
    public boolean l;
    public boolean m;

    private final void setParentContext(th thVar) {
        if (this.h != thVar) {
            this.h = thVar;
            if (thVar != null) {
                this.e = null;
            }
            f81 f81Var = this.g;
            if (f81Var != null) {
                f81Var.g();
                this.g = null;
                if (isAttachedToWindow()) {
                    e();
                }
            }
        }
    }

    private final void setPreviousAttachedWindowToken(IBinder iBinder) {
        if (this.f != iBinder) {
            this.f = iBinder;
            this.e = null;
        }
    }

    public abstract void a(bw bwVar, int i);

    @Override // android.view.ViewGroup
    public final void addView(View view) {
        c();
        super.addView(view);
    }

    @Override // android.view.ViewGroup
    public final boolean addViewInLayout(View view, int i, ViewGroup.LayoutParams layoutParams) {
        c();
        return super.addViewInLayout(view, i, layoutParams);
    }

    public final void b() {
        if (isAttachedToWindow()) {
            setPreviousAttachedWindowToken(getWindowToken());
            if (this.i == null) {
                b4 b4Var = null;
                if (getChildCount() != 0) {
                    View childAt = getChildAt(0);
                    if (childAt instanceof b4) {
                        b4Var = (b4) childAt;
                    }
                }
                if (b4Var != null) {
                    b4Var.setComposeViewContext(h(o4.C(this), b4Var.getComposeViewContext()));
                }
            }
            if (getShouldCreateCompositionOnAttachedToWindow()) {
                e();
            }
        }
    }

    public final void c() {
        if (this.l) {
            return;
        }
        throw new UnsupportedOperationException("Cannot add views to " + getClass().getSimpleName() + "; only Compose content is supported");
    }

    public final void d() {
        b4 b4Var;
        View childAt = getChildAt(0);
        if (childAt instanceof b4) {
            b4Var = (b4) childAt;
        } else {
            b4Var = null;
        }
        if (b4Var != null && b4Var.L0) {
            b4Var.getComposeViewContext().b();
            b4Var.L0 = false;
        }
        f81 f81Var = this.g;
        if (f81Var != null) {
            f81Var.g();
        }
        this.g = null;
        requestLayout();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void e() {
        if (this.g == null) {
            boolean z = false;
            Object[] objArr = 0;
            try {
                this.l = true;
                Trace.beginSection("Compose:initializeView");
                try {
                    nh nhVar = this.i;
                    if (nhVar == null) {
                        nhVar = f();
                    }
                    this.g = h81.a(this, nhVar, new gg(1003123809, true, new o(objArr == true ? 1 : 0, this)));
                    Trace.endSection();
                } catch (Throwable th) {
                    Trace.endSection();
                    throw th;
                }
            } finally {
                this.l = false;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final defpackage.nh f() {
        /*
            r9 = this;
            int r0 = r9.getChildCount()
            r1 = 0
            if (r0 != 0) goto L9
        L7:
            r0 = r1
            goto L1c
        L9:
            r0 = 0
            android.view.View r0 = r9.getChildAt(r0)
            boolean r2 = r0 instanceof defpackage.b4
            if (r2 == 0) goto L15
            b4 r0 = (defpackage.b4) r0
            goto L16
        L15:
            r0 = r1
        L16:
            if (r0 == 0) goto L7
            nh r0 = r0.getComposeViewContext()
        L1c:
            android.view.View r4 = defpackage.o4.C(r9)
            nh r2 = defpackage.o4.D(r4)
            if (r2 != 0) goto L7c
            th r5 = r9.g()
            j80 r9 = defpackage.g30.q(r4)
            if (r9 != 0) goto L38
            if (r0 == 0) goto L35
            j80 r9 = r0.c
            goto L36
        L35:
            r9 = r1
        L36:
            if (r9 == 0) goto L3a
        L38:
            r6 = r9
            goto L40
        L3a:
            java.lang.String r9 = "Composed into the View which doesn't propagate ViewTreeLifecycleOwner!"
            defpackage.v7.o(r9)
            return r1
        L40:
            ps0 r9 = defpackage.n30.t(r4)
            if (r9 != 0) goto L4e
            if (r0 == 0) goto L4b
            ps0 r9 = r0.d
            goto L4c
        L4b:
            r9 = r1
        L4c:
            if (r9 == 0) goto L50
        L4e:
            r7 = r9
            goto L56
        L50:
            java.lang.String r9 = "Composed into the View which doesn't propagate ViewTreeSavedStateRegistryOwner!"
            defpackage.v7.o(r9)
            return r1
        L56:
            w51 r9 = defpackage.o30.o(r4)
            if (r9 != 0) goto L62
            if (r0 == 0) goto L60
            w51 r1 = r0.e
        L60:
            r8 = r1
            goto L63
        L62:
            r8 = r9
        L63:
            nh r2 = new nh
            android.view.View r9 = defpackage.o4.C(r4)
            nh r3 = defpackage.o4.D(r9)
            r2.<init>(r3, r4, r5, r6, r7, r8)
            java.lang.ref.WeakReference r9 = new java.lang.ref.WeakReference
            r9.<init>(r2)
            r0 = 2131034154(0x7f05002a, float:1.7678818E38)
            r4.setTag(r0, r9)
            return r2
        L7c:
            nh r9 = r9.h(r4, r2)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.p.f():nh");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v4, types: [ep0, java.lang.Object] */
    public final th g() {
        to0 to0Var;
        yj yjVar;
        q6 q6Var;
        l80 l80Var;
        th thVar;
        th thVar2 = this.h;
        if (thVar2 == null) {
            thVar2 = b81.a(this);
            if (thVar2 == null) {
                Object parent = getParent();
                while (thVar2 == null && (parent instanceof View)) {
                    View view = (View) parent;
                    thVar2 = b81.a(view);
                    parent = y20.j(view);
                }
            }
            ij ijVar = null;
            if (thVar2 != null) {
                if ((thVar2 instanceof to0) && ((po0) ((to0) thVar2).u.getValue()).compareTo(po0.f) <= 0) {
                    thVar = null;
                } else {
                    thVar = thVar2;
                }
                if (thVar != null) {
                    this.e = new WeakReference(thVar);
                }
            } else {
                thVar2 = null;
            }
            if (thVar2 == null) {
                WeakReference weakReference = this.e;
                if (weakReference == null || (thVar2 = (th) weakReference.get()) == null || ((thVar2 instanceof to0) && ((po0) ((to0) thVar2).u.getValue()).compareTo(po0.f) <= 0)) {
                    thVar2 = null;
                }
                if (thVar2 == null) {
                    if (!isAttachedToWindow()) {
                        q00.b("Cannot locate windowRecomposer; View " + this + " is not attached to a window");
                    }
                    Object j = y20.j(this);
                    View view2 = this;
                    while (j instanceof View) {
                        View view3 = (View) j;
                        if (view3.getId() == 16908290) {
                            break;
                        }
                        view2 = view3;
                        j = view3.getParent();
                    }
                    th a = b81.a(view2);
                    if (a == null) {
                        ((v71) w71.a.get()).getClass();
                        cr crVar = cr.e;
                        a01 a01Var = n6.q;
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                            yjVar = (yj) n6.q.getValue();
                        } else {
                            yjVar = (yj) n6.r.get();
                            if (yjVar == null) {
                                v7.o("no AndroidUiDispatcher for this thread");
                                return null;
                            }
                        }
                        yj i = yjVar.i(crVar);
                        q6 q6Var2 = (q6) i.j(x1.P);
                        if (q6Var2 != null) {
                            q6 q6Var3 = new q6(q6Var2);
                            c9 c9Var = (c9) q6Var3.g;
                            synchronized (c9Var.c) {
                                c9Var.b = false;
                                q6Var = q6Var3;
                            }
                        } else {
                            q6Var = 0;
                        }
                        ?? obj = new Object();
                        yj yjVar2 = (id0) i.j(x1.Q);
                        if (yjVar2 == null) {
                            yjVar2 = new jd0(view2.getContext().getApplicationContext());
                            obj.e = yjVar2;
                        }
                        if (q6Var != 0) {
                            crVar = q6Var;
                        }
                        yj i2 = i.i(crVar).i(yjVar2);
                        to0 to0Var2 = new to0(i2);
                        to0Var2.K();
                        hj d = dl.d(i2);
                        j80 q = g30.q(view2);
                        if (q != null) {
                            l80Var = q.f();
                        } else {
                            l80Var = null;
                        }
                        if (l80Var != null) {
                            view2.addOnAttachStateChangeListener(new x71(view2, to0Var2));
                            l80Var.a(new z71(d, q6Var, to0Var2, obj));
                            view2.setTag(R.id.androidx_compose_ui_view_composition_context, to0Var2);
                            yw ywVar = yw.e;
                            Handler handler = view2.getHandler();
                            int i3 = zx.a;
                            view2.addOnAttachStateChangeListener(new m5(2, f31.G(ywVar, new xx(handler, "windowRecomposer cleanup", false).j, new d(to0Var2, view2, ijVar, 22), 2)));
                            to0Var = to0Var2;
                        } else {
                            q00.c("ViewTreeLifecycleOwner not found from " + view2);
                            throw new RuntimeException();
                        }
                    } else if (a instanceof to0) {
                        to0Var = (to0) a;
                    } else {
                        v7.o("root viewTreeParentCompositionContext is not a Recomposer");
                        return null;
                    }
                    if (((po0) to0Var.u.getValue()).compareTo(po0.f) > 0) {
                        ijVar = to0Var;
                    }
                    if (ijVar != null) {
                        this.e = new WeakReference(ijVar);
                    }
                    return to0Var;
                }
            }
        }
        return thVar2;
    }

    /* renamed from: getAutoClearFocusBehavior-4UtRPd4, reason: not valid java name */
    public final int m9getAutoClearFocusBehavior4UtRPd4() {
        p8 p8Var;
        Object tag = getTag(R.id.auto_clear_focus_behavior_tag);
        if (tag instanceof p8) {
            p8Var = (p8) tag;
        } else {
            p8Var = null;
        }
        if (p8Var != null) {
            return p8Var.a;
        }
        return 1;
    }

    public final nh getComposeViewContext$ui() {
        return this.i;
    }

    public final boolean getHasComposition() {
        if (this.g != null) {
            return true;
        }
        return false;
    }

    public boolean getShouldCreateCompositionOnAttachedToWindow() {
        return true;
    }

    public final boolean getShowLayoutBounds() {
        return this.k;
    }

    public final nh h(View view, nh nhVar) {
        ps0 ps0Var;
        th g = g();
        j80 q = g30.q(view);
        w51 o = o30.o(view);
        ps0 t = n30.t(view);
        th thVar = nhVar.b;
        ps0 ps0Var2 = nhVar.d;
        j80 j80Var = nhVar.c;
        if (g == thVar && q == j80Var && o == nhVar.e && t == ps0Var2) {
            return nhVar;
        }
        if (g.j() != nhVar.b.j()) {
            d();
        }
        if (q == null) {
            q = j80Var;
        }
        if (t == null) {
            ps0Var = ps0Var2;
        } else {
            ps0Var = t;
        }
        nh nhVar2 = new nh(nhVar, view, g, q, ps0Var, o);
        view.setTag(R.id.androidx_compose_ui_view_compose_view_context, new WeakReference(nhVar2));
        return nhVar2;
    }

    @Override // android.view.ViewGroup
    public final boolean isTransitionGroup() {
        if (this.m && !super.isTransitionGroup()) {
            return false;
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ve0 ve0Var = b81.a;
        Object j = y20.j(this);
        View view = this;
        while (j instanceof View) {
            View view2 = (View) j;
            if (view2.getId() == 16908290) {
                break;
            }
            view = view2;
            j = view2.getParent();
        }
        if (view.getParent() == null) {
            getHandler().postAtFrontOfQueue(new n(0, this));
        } else {
            b();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View childAt = getChildAt(0);
        if (childAt != null) {
            childAt.layout(getPaddingLeft(), getPaddingTop(), (i3 - i) - getPaddingRight(), (i4 - i2) - getPaddingBottom());
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        e();
        View childAt = getChildAt(0);
        if (childAt == null) {
            super.onMeasure(i, i2);
            return;
        }
        childAt.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i) - getPaddingLeft()) - getPaddingRight()), View.MeasureSpec.getMode(i)), View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i2) - getPaddingTop()) - getPaddingBottom()), View.MeasureSpec.getMode(i2)));
        setMeasuredDimension(getPaddingRight() + getPaddingLeft() + childAt.getMeasuredWidth(), getPaddingBottom() + getPaddingTop() + childAt.getMeasuredHeight());
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        View childAt = getChildAt(0);
        if (childAt != null) {
            childAt.setLayoutDirection(i);
        }
    }

    /* renamed from: setAutoClearFocusBehavior-17tfJxM, reason: not valid java name */
    public final void m10setAutoClearFocusBehavior17tfJxM(int i) {
        setTag(R.id.auto_clear_focus_behavior_tag, new p8(i));
    }

    public final void setComposeViewContext$ui(nh nhVar) {
        b4 b4Var;
        if (this.i != nhVar) {
            if (nhVar == null) {
                d();
            } else if (getChildCount() != 0) {
                View childAt = getChildAt(0);
                if (childAt instanceof b4) {
                    b4Var = (b4) childAt;
                } else {
                    b4Var = null;
                }
                if (b4Var != null) {
                    if (b4Var.getCoroutineContext() != nhVar.b.j()) {
                        d();
                    }
                    b4Var.setComposeViewContext(nhVar);
                }
            }
            this.i = nhVar;
        }
    }

    public final void setParentCompositionContext(th thVar) {
        setParentContext(thVar);
    }

    public final void setShowLayoutBounds(boolean z) {
        this.k = z;
        KeyEvent.Callback childAt = getChildAt(0);
        if (childAt != null) {
            ((b4) ((mj0) childAt)).setShowLayoutBounds(z);
        }
    }

    @Override // android.view.ViewGroup
    public void setTransitionGroup(boolean z) {
        super.setTransitionGroup(z);
        this.m = true;
    }

    public final void setViewCompositionStrategy(k51 k51Var) {
        r90 r90Var = this.j;
        if (r90Var != null) {
            r90Var.a();
        }
        ((t20) k51Var).getClass();
        int i = 1;
        m5 m5Var = new m5(i, this);
        addOnAttachStateChangeListener(m5Var);
        v7 v7Var = new v7(26);
        o30.s(this).a.add(v7Var);
        this.j = new r90(this, m5Var, v7Var, i);
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i) {
        c();
        super.addView(view, i);
    }

    @Override // android.view.ViewGroup
    public final boolean addViewInLayout(View view, int i, ViewGroup.LayoutParams layoutParams, boolean z) {
        c();
        return super.addViewInLayout(view, i, layoutParams, z);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, int i2) {
        c();
        super.addView(view, i, i2);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        c();
        super.addView(view, layoutParams);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        c();
        super.addView(view, i, layoutParams);
    }

    public static /* synthetic */ void getComposeViewContext$ui$annotations() {
    }

    private static /* synthetic */ void getDisposeViewCompositionStrategy$annotations() {
    }

    public static /* synthetic */ void getShowLayoutBounds$annotations() {
    }
}

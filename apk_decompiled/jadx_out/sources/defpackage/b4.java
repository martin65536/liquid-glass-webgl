package defpackage;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.os.Trace;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.ScrollCaptureTarget;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStructure;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.translation.ViewTranslationRequest;
import com.kyant.backdrop.catalog.R;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class b4 extends ViewGroup implements mj0, er0, yl, ej0, ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener, ViewTreeObserver.OnTouchModeChangeListener, gt {
    public static Class P0;
    public static Method Q0;
    public static Method R0;
    public static final pe0 S0 = new pe0();
    public static o3 T0;
    public static Method U0;
    public final he0 A;
    public final c4 A0;
    public final yo0 B;
    public final pe0 B0;
    public final vu0 C;
    public float C0;
    public final h4 D;
    public float D0;
    public t4 E;
    public final z3 E0;
    public final c3 F;
    public final m3 F0;
    public final n5 G;
    public boolean G0;
    public final u8 H;
    public final m00 H0;
    public final pe0 I;
    public final t3 I0;
    public pe0 J;
    public final gc J0;
    public boolean K;
    public boolean K0;
    public final md0 L;
    public boolean L0;
    public final ud M;
    public final t70 M0;
    public final ik0 N;
    public View N0;
    public final ym O;
    public final dt0 O0;
    public final e3 P;
    public final g3 Q;
    public boolean R;
    public final l3 S;
    public final k3 T;
    public final pj0 U;
    public boolean V;
    public t6 W;
    public si a0;
    public boolean b0;
    public final mc0 c0;
    public long d0;
    public final ik0 e;
    public final int[] e0;
    public long f;
    public final float[] f0;
    public final boolean g;
    public final float[] g0;
    public b00 h;
    public long h0;
    public final b50 i;
    public boolean i0;
    public m80 j;
    public long j0;
    public n80 k;
    public final ik0 k0;
    public nq0 l;
    public final ym l0;
    public final a8 m;
    public gv m0;
    public final m3 n;
    public e11 n0;
    public final ik0 o;
    public d11 o0;
    public final View p;
    public final AtomicReference p0;
    public final lt q;
    public dt0 q0;
    public yj r;
    public final vt r0;
    public final a5 s;
    public final af0 s0;
    public final t70 t;
    public final ik0 t0;
    public final ik0 u;
    public final ay u0;
    public final ym v;
    public final f10 v0;
    public final zc w;
    public final dd0 w0;
    public final r6 x;
    public final dt0 x0;
    public final j10 y;
    public MotionEvent y0;
    public final z40 z;
    public long z0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r0v30, types: [java.lang.Object, ud] */
    /* JADX WARN: Type inference failed for: r0v64, types: [java.lang.Object, e3] */
    /* JADX WARN: Type inference failed for: r1v38, types: [dd0, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v7, types: [gr, bd0] */
    public b4(Context context, nh nhVar) {
        super(context);
        e3 e3Var;
        g3 g3Var;
        m40 m40Var;
        int i;
        gc hcVar;
        AutofillId autofillId;
        b4 b4Var = this;
        b4Var.e = n30.B(nhVar);
        b4Var.f = 9205357640488583168L;
        int i2 = 1;
        b4Var.g = true;
        b4Var.i = nhVar.r;
        b4Var.l = x1.I;
        b4Var.m = new a8();
        int i3 = 0;
        b4Var.n = new m3(b4Var, i3);
        b4Var.o = new ik0(jc0.b(context), x1.V);
        b4Var.q = new lt(b4Var, b4Var);
        b4Var.r = nhVar.b.j();
        b4Var.s = new a5();
        b4Var.t = new t70(0);
        b4Var.u = n30.B(Boolean.FALSE);
        b4Var.v = n30.r(new t3(b4Var, i3));
        b4Var.w = nhVar.t;
        b4Var.x = nhVar.q;
        b4Var.y = new j10();
        int i4 = 3;
        z40 z40Var = new z40(3);
        pc0 pc0Var = z40Var.z;
        fr0 fr0Var = fr0.b;
        if (!o20.e(pc0Var, fr0Var)) {
            z40Var.z = fr0Var;
            z40Var.B();
        }
        z40Var.X(b4Var.getDensity());
        z40Var.b0(b4Var.getViewConfiguration());
        z40Var.a0(d3.d(new a4(b4Var), ((lt) b4Var.getFocusOwner()).e).b(b4Var.m6getDragAndDropManager().c));
        b4Var.z = z40Var;
        he0 he0Var = u10.a;
        b4Var.A = new he0();
        b4Var.m7getLayoutNodes();
        b4Var.B = new yo0(b4Var);
        b4Var.C = new vu0(b4Var.getRoot(), new bd0(), b4Var.m7getLayoutNodes());
        h4 h4Var = new h4(b4Var);
        b4Var.D = h4Var;
        b4Var.E = new t4(b4Var, new s3(0, b4Var, o4.class, "getContentCaptureSessionCompat", "getContentCaptureSessionCompat(Landroid/view/View;)Landroidx/compose/ui/contentcapture/ContentCaptureSessionWrapper;", 1, 0, 0));
        b4Var.F = nhVar.j;
        b4Var.G = new n5(b4Var);
        b4Var.H = new u8();
        b4Var.I = new pe0();
        b4Var.L = new md0();
        z40 root = b4Var.getRoot();
        ?? obj = new Object();
        obj.b = root;
        obj.c = new my(root.H.c);
        obj.d = new j2(17);
        obj.e = new py();
        b4Var.M = obj;
        b4Var.N = n30.B(new Configuration(context.getResources().getConfiguration()));
        b4Var.O = n30.r(new t3(b4Var, i2));
        if (k()) {
            u8 autofillTree = b4Var.getAutofillTree();
            ?? obj2 = new Object();
            obj2.a = b4Var;
            obj2.b = autofillTree;
            AutofillManager g = z0.g(b4Var.getContext().getSystemService(z0.l()));
            if (g != null) {
                obj2.c = g;
                b4Var.setImportantForAutofill(1);
                j1 t = m20.t(b4Var);
                if (t != null) {
                    autofillId = z0.e(t.a);
                } else {
                    autofillId = null;
                }
                if (autofillId != null) {
                    obj2.d = autofillId;
                    e3Var = obj2;
                } else {
                    throw d3.t("Required value was null.");
                }
            } else {
                v7.o("Autofill service could not be located.");
                throw null;
            }
        } else {
            e3Var = null;
        }
        b4Var.P = e3Var;
        if (k()) {
            AutofillManager g2 = z0.g(context.getSystemService(z0.l()));
            if (g2 != null) {
                b4Var = this;
                g3Var = new g3(new j2(15, g2), getSemanticsOwner(), this, getRectManager(), context.getPackageName());
            } else {
                throw d3.t("Autofill service could not be located.");
            }
        } else {
            g3Var = null;
        }
        b4Var.Q = g3Var;
        b4Var.S = nhVar.l;
        b4Var.T = nhVar.m;
        b4Var.U = new pj0(new x3(b4Var, i2));
        b4Var.c0 = new mc0(b4Var.getRoot());
        b4Var.d0 = 9223372034707292159L;
        b4Var.e0 = new int[]{0, 0};
        float[] n = m20.n();
        b4Var.f0 = m20.n();
        b4Var.g0 = m20.n();
        b4Var.h0 = -1L;
        b4Var.j0 = 9187343241974906880L;
        b4Var.k0 = n30.B(null);
        b4Var.l0 = n30.r(new t3(b4Var, i4));
        b4Var.p0 = new AtomicReference(null);
        b4Var.r0 = nhVar.n;
        b4Var.s0 = nhVar.o;
        int layoutDirection = context.getResources().getConfiguration().getLayoutDirection();
        int[] iArr = et.a;
        m40 m40Var2 = m40.e;
        if (layoutDirection != 0) {
            if (layoutDirection != 1) {
                m40Var = null;
            } else {
                m40Var = m40.f;
            }
        } else {
            m40Var = m40Var2;
        }
        b4Var.t0 = n30.B(m40Var != null ? m40Var : m40Var2);
        b4Var.u0 = nhVar.p;
        int i5 = 2;
        if (b4Var.isInTouchMode()) {
            i = 1;
        } else {
            i = 2;
        }
        b4Var.v0 = new f10(i);
        ?? obj3 = new Object();
        new ef0(new r9[16]);
        new ef0(new n30[16]);
        new ef0(new z40[16]);
        new ef0(new n30[16]);
        b4Var.w0 = obj3;
        b4Var.x0 = new dt0(11);
        b4Var.A0 = new c4(25);
        b4Var.B0 = new pe0();
        b4Var.E0 = new z3(i3, b4Var);
        b4Var.F0 = new m3(b4Var, i2);
        b4Var.H0 = new m00(context, new x3(b4Var, i3));
        b4Var.I0 = new t3(b4Var, i5);
        int i6 = Build.VERSION.SDK_INT;
        if (i6 < 29) {
            hcVar = new c4(n);
        } else {
            hcVar = new hc();
        }
        b4Var.J0 = hcVar;
        b4Var.addOnAttachStateChangeListener(b4Var.E);
        b4Var.setWillNotDraw(false);
        b4Var.setFocusable(true);
        if (i6 >= 26) {
            n4.a.a(b4Var, 1, false);
        }
        b4Var.setFocusableInTouchMode(true);
        b4Var.setClipChildren(false);
        int i7 = j51.a;
        if (b4Var.getImportantForAccessibility() == 0) {
            b4Var.setImportantForAccessibility(1);
        }
        b4Var.setAccessibilityDelegate(h4Var.f);
        b4Var.setOnDragListener(b4Var.m6getDragAndDropManager());
        b4Var.getRoot().d(b4Var);
        if (i6 >= 29) {
            j4.a.a(b4Var);
        }
        if (s()) {
            View view = new View(context);
            view.setLayoutParams(new ViewGroup.LayoutParams(1, 1));
            view.setTag(R.id.hide_in_inspector_tag, Boolean.TRUE);
            b4Var.p = view;
            b4Var.addView(view, -1);
        }
        b4Var.M0 = i6 >= 31 ? new t70(1) : null;
        b4Var.O0 = new dt0(b4Var);
    }

    private final boolean getDerivedIsAttached() {
        return ((Boolean) this.v.getValue()).booleanValue();
    }

    private final e11 getLegacyTextInputServiceAndroid() {
        e11 e11Var = this.n0;
        if (e11Var == null) {
            e11 e11Var2 = new e11(getView(), this);
            this.n0 = e11Var2;
            return e11Var2;
        }
        return e11Var;
    }

    private final nh get_composeViewContext() {
        return (nh) this.e.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final r3 get_viewTreeOwners() {
        d3.z(this.k0.getValue());
        return null;
    }

    public static boolean k() {
        if (Build.VERSION.SDK_INT >= 26) {
            return true;
        }
        return false;
    }

    public static void l(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof b4) {
                ((b4) childAt).z();
            } else if (childAt instanceof ViewGroup) {
                l((ViewGroup) childAt);
            }
        }
    }

    public static long m(int i) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode != Integer.MIN_VALUE) {
            if (mode != 0) {
                if (mode == 1073741824) {
                    long j = size;
                    return j | (j << 32);
                }
                throw new IllegalStateException();
            }
            return 2147483647L;
        }
        return size;
    }

    public static View n(View view, int i) {
        if (Build.VERSION.SDK_INT < 29) {
            Method declaredMethod = View.class.getDeclaredMethod("getAccessibilityViewId", null);
            declaredMethod.setAccessible(true);
            if (o20.e(declaredMethod.invoke(view, null), Integer.valueOf(i))) {
                return view;
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View n = n(viewGroup.getChildAt(i2), i);
                    if (n != null) {
                        return n;
                    }
                }
            }
        }
        return null;
    }

    public static void q(z40 z40Var) {
        z40Var.A();
        ef0 w = z40Var.w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            q((z40) objArr[i2]);
        }
    }

    public static boolean s() {
        if (Build.VERSION.SDK_INT >= 35) {
            return true;
        }
        return false;
    }

    private final void setAttached(boolean z) {
        this.u.setValue(Boolean.valueOf(z));
    }

    private void setDensity(mm mmVar) {
        this.o.setValue(mmVar);
    }

    private void setFontFamilyResolver(wt wtVar) {
        this.s0.setValue(wtVar);
    }

    private void setLayoutDirection(m40 m40Var) {
        this.t0.setValue(m40Var);
    }

    private final void set_composeViewContext(nh nhVar) {
        this.e.setValue(nhVar);
    }

    private final void set_viewTreeOwners(r3 r3Var) {
        this.k0.setValue(r3Var);
    }

    public static boolean t(MotionEvent motionEvent) {
        boolean z;
        if ((Float.floatToRawIntBits(motionEvent.getX()) & Integer.MAX_VALUE) < 2139095040 && (Float.floatToRawIntBits(motionEvent.getY()) & Integer.MAX_VALUE) < 2139095040 && (Float.floatToRawIntBits(motionEvent.getRawX()) & Integer.MAX_VALUE) < 2139095040 && (Float.floatToRawIntBits(motionEvent.getRawY()) & Integer.MAX_VALUE) < 2139095040) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            int pointerCount = motionEvent.getPointerCount();
            for (int i = 1; i < pointerCount; i++) {
                if ((Float.floatToRawIntBits(motionEvent.getX(i)) & Integer.MAX_VALUE) < 2139095040 && (Float.floatToRawIntBits(motionEvent.getY(i)) & Integer.MAX_VALUE) < 2139095040 && (Build.VERSION.SDK_INT < 29 || nd0.a.a(motionEvent, i))) {
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    break;
                }
            }
        }
        return z;
    }

    public final void A(z40 z40Var) {
        h4 h4Var = this.D;
        h4Var.B = true;
        if (h4Var.n()) {
            h4Var.o(z40Var);
        }
        t4 t4Var = this.E;
        t4Var.k = true;
        if (t4Var.k()) {
            t4Var.l.q(x31.a);
        }
    }

    public final void B(z40 z40Var, boolean z, boolean z2, boolean z3) {
        z40 s;
        z40 s2;
        mc0 mc0Var = this.c0;
        if (z) {
            r7 r7Var = mc0Var.b;
            z40 z40Var2 = z40Var.l;
            d50 d50Var = z40Var.I;
            if (z40Var2 == null) {
                q00.b("Error: requestLookaheadRemeasure cannot be called on a node outside LookaheadScope");
            }
            int ordinal = d50Var.d.ordinal();
            if (ordinal != 0) {
                if (ordinal != 1) {
                    if (ordinal != 2 && ordinal != 3) {
                        if (ordinal == 4) {
                            if (!d50Var.e || z2) {
                                d50Var.e = true;
                                d50Var.p.x = true;
                                if (!z40Var.Q) {
                                    if ((!o20.e(z40Var.G(), Boolean.TRUE) && !mc0.i(z40Var)) || ((s = z40Var.s()) != null && s.I.e)) {
                                        if ((z40Var.F() || mc0.j(z40Var)) && ((s2 = z40Var.s()) == null || !s2.p())) {
                                            r7Var.k(z40Var, u20.g);
                                        }
                                    } else {
                                        r7Var.k(z40Var, u20.e);
                                    }
                                    if (!mc0Var.d && z3) {
                                        H(z40Var);
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        v7.k();
                        return;
                    }
                } else {
                    return;
                }
            }
            mc0Var.h.b(new lc0(z40Var, true, z2));
            return;
        }
        if (mc0Var.r(z40Var, z2) && z3) {
            H(z40Var);
        }
    }

    public final void C(z40 z40Var, boolean z, boolean z2) {
        boolean z3;
        d50 d50Var = z40Var.I;
        u20 u20Var = u20.h;
        mc0 mc0Var = this.c0;
        if (z) {
            r7 r7Var = mc0Var.b;
            int ordinal = d50Var.d.ordinal();
            if (ordinal != 0) {
                if (ordinal != 1) {
                    if (ordinal != 2) {
                        if (ordinal != 3) {
                            if (ordinal != 4) {
                                v7.k();
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                } else {
                    return;
                }
            }
            if ((!d50Var.e && !d50Var.f) || z2) {
                d50Var.f = true;
                d50Var.g = true;
                oc0 oc0Var = d50Var.p;
                oc0Var.y = true;
                oc0Var.z = true;
                if (!z40Var.Q) {
                    z40 s = z40Var.s();
                    if (o20.e(z40Var.G(), Boolean.TRUE) && ((s == null || !s.I.e) && (s == null || !s.I.f))) {
                        r7Var.k(z40Var, u20.f);
                    } else if (z40Var.F() && ((s == null || !s.o()) && (s == null || !s.p()))) {
                        r7Var.k(z40Var, u20Var);
                    }
                    if (!mc0Var.d) {
                        H(null);
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        mc0Var.getClass();
        int ordinal2 = d50Var.d.ordinal();
        if (ordinal2 != 0 && ordinal2 != 1 && ordinal2 != 2 && ordinal2 != 3) {
            if (ordinal2 == 4) {
                z40 s2 = z40Var.s();
                if (s2 != null && !s2.F()) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                if (!z2) {
                    if (!z40Var.p()) {
                        if (z40Var.o() && z40Var.F() == z3 && z40Var.F() == d50Var.p.w) {
                            return;
                        }
                    } else {
                        return;
                    }
                }
                oc0 oc0Var2 = d50Var.p;
                oc0Var2.y = true;
                oc0Var2.z = true;
                if (!z40Var.Q && oc0Var2.w && z3) {
                    if ((s2 == null || !s2.o()) && (s2 == null || !s2.p())) {
                        mc0Var.b.k(z40Var, u20Var);
                    }
                    if (!mc0Var.d) {
                        H(null);
                        return;
                    }
                    return;
                }
                return;
            }
            v7.k();
        }
    }

    public final void D() {
        h4 h4Var = this.D;
        h4Var.B = true;
        Handler handler = h4Var.h.getHandler();
        if (h4Var.n() && !h4Var.M && handler != null) {
            h4Var.M = true;
            handler.post(h4Var.O);
        }
        t4 t4Var = this.E;
        t4Var.k = true;
        Handler handler2 = t4Var.e.getHandler();
        if (t4Var.k() && !t4Var.q && handler2 != null) {
            t4Var.q = true;
            handler2.post(t4Var.r);
        }
    }

    public final void E() {
        if (!this.i0) {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            if (currentAnimationTimeMillis != this.h0) {
                this.h0 = currentAnimationTimeMillis;
                gc gcVar = this.J0;
                float[] fArr = this.f0;
                gcVar.e(this, fArr);
                y20.m(fArr, this.g0);
                ViewParent parent = getParent();
                View view = this;
                while (parent instanceof ViewGroup) {
                    view = (View) parent;
                    parent = ((ViewGroup) view).getParent();
                }
                int[] iArr = this.e0;
                view.getLocationOnScreen(iArr);
                float f = iArr[0];
                float f2 = iArr[1];
                view.getLocationInWindow(iArr);
                float f3 = iArr[0];
                float f4 = f2 - iArr[1];
                this.j0 = (Float.floatToRawIntBits(f - f3) << 32) | (Float.floatToRawIntBits(f4) & 4294967295L);
            }
        }
    }

    public final void F(MotionEvent motionEvent) {
        this.h0 = AnimationUtils.currentAnimationTimeMillis();
        gc gcVar = this.J0;
        float[] fArr = this.f0;
        gcVar.e(this, fArr);
        y20.m(fArr, this.g0);
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        long y2 = m20.y(fArr, (Float.floatToRawIntBits(x) << 32) | (Float.floatToRawIntBits(y) & 4294967295L));
        float rawX = motionEvent.getRawX() - Float.intBitsToFloat((int) (y2 >> 32));
        float rawY = motionEvent.getRawY() - Float.intBitsToFloat((int) (y2 & 4294967295L));
        this.j0 = (Float.floatToRawIntBits(rawX) << 32) | (Float.floatToRawIntBits(rawY) & 4294967295L);
    }

    public final boolean G() {
        if (isFocused()) {
            return true;
        }
        return super.requestFocus(130, null);
    }

    public final void H(z40 z40Var) {
        if (!isLayoutRequested() && isAttachedToWindow()) {
            if (z40Var != null) {
                while (z40Var != null && z40Var.q() == x40.e) {
                    if (!this.b0) {
                        z40 s = z40Var.s();
                        if (s == null) {
                            break;
                        }
                        long j = s.H.c.h;
                        if (si.f(j) && si.e(j)) {
                            break;
                        }
                    }
                    z40Var = z40Var.s();
                }
                if (z40Var == getRoot()) {
                    requestLayout();
                    return;
                }
            }
            if (getWidth() != 0 && getHeight() != 0) {
                invalidate();
            } else {
                requestLayout();
            }
        }
    }

    public final long I(long j) {
        E();
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) - Float.intBitsToFloat((int) (this.j0 >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) - Float.intBitsToFloat((int) (this.j0 & 4294967295L));
        return m20.y(this.g0, (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32));
    }

    public final int J(MotionEvent motionEvent) {
        Object obj;
        if (this.K0) {
            this.K0 = false;
            t70 t70Var = getComposeViewContext().s;
            int metaState = motionEvent.getMetaState();
            t70Var.getClass();
            e61.a.setValue(new zm0(metaState));
        }
        md0 md0Var = this.L;
        c4 c = md0Var.c(motionEvent, this);
        int actionMasked = motionEvent.getActionMasked();
        ud udVar = this.M;
        if (c != null) {
            List list = (List) c.f;
            int size = list.size() - 1;
            if (size >= 0) {
                while (true) {
                    int i = size - 1;
                    obj = list.get(size);
                    if (((wm0) obj).e && (actionMasked == 0 || actionMasked == 5)) {
                        break;
                    }
                    if (i < 0) {
                        break;
                    }
                    size = i;
                }
            }
            obj = null;
            wm0 wm0Var = (wm0) obj;
            if (wm0Var != null) {
                this.f = wm0Var.d;
            }
            int a = udVar.a(c, this, u(motionEvent));
            c.g = null;
            if ((actionMasked != 0 && actionMasked != 5) || (a & 1) != 0) {
                return a;
            }
            int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
            md0Var.c.delete(pointerId);
            md0Var.b.delete(pointerId);
            return a;
        }
        if (!udVar.a) {
            kb0 kb0Var = (kb0) ((j2) udVar.d).f;
            int i2 = kb0Var.h;
            Object[] objArr = kb0Var.g;
            for (int i3 = 0; i3 < i2; i3++) {
                objArr[i3] = null;
            }
            kb0Var.h = 0;
            kb0Var.e = false;
            ((my) udVar.c).c();
        }
        return 0;
    }

    public final void K(MotionEvent motionEvent, int i, long j, boolean z) {
        int i2;
        int buttonState;
        long downTime;
        int i3;
        int actionMasked = motionEvent.getActionMasked();
        int i4 = -1;
        if (actionMasked != 1) {
            if (actionMasked == 6) {
                i4 = motionEvent.getActionIndex();
            }
        } else if (i != 9 && i != 10) {
            i4 = 0;
        }
        int pointerCount = motionEvent.getPointerCount();
        if (i4 >= 0) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        int i5 = pointerCount - i2;
        if (i5 == 0) {
            return;
        }
        MotionEvent.PointerProperties[] pointerPropertiesArr = new MotionEvent.PointerProperties[i5];
        for (int i6 = 0; i6 < i5; i6++) {
            pointerPropertiesArr[i6] = new MotionEvent.PointerProperties();
        }
        MotionEvent.PointerCoords[] pointerCoordsArr = new MotionEvent.PointerCoords[i5];
        for (int i7 = 0; i7 < i5; i7++) {
            pointerCoordsArr[i7] = new MotionEvent.PointerCoords();
        }
        for (int i8 = 0; i8 < i5; i8++) {
            if (i4 >= 0 && i8 >= i4) {
                i3 = 1;
            } else {
                i3 = 0;
            }
            int i9 = i3 + i8;
            motionEvent.getPointerProperties(i9, pointerPropertiesArr[i8]);
            MotionEvent.PointerCoords pointerCoords = pointerCoordsArr[i8];
            motionEvent.getPointerCoords(i9, pointerCoords);
            float f = pointerCoords.x;
            float f2 = pointerCoords.y;
            long w = w((Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
            pointerCoords.x = Float.intBitsToFloat((int) (w >> 32));
            pointerCoords.y = Float.intBitsToFloat((int) (w & 4294967295L));
        }
        if (z) {
            buttonState = 0;
        } else {
            buttonState = motionEvent.getButtonState();
        }
        if (motionEvent.getDownTime() == motionEvent.getEventTime()) {
            downTime = j;
        } else {
            downTime = motionEvent.getDownTime();
        }
        MotionEvent obtain = MotionEvent.obtain(downTime, j, i, i5, pointerPropertiesArr, pointerCoordsArr, motionEvent.getMetaState(), buttonState, motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getFlags());
        c4 c = this.L.c(obtain, this);
        c.getClass();
        this.M.a(c, this, true);
        obtain.recycle();
    }

    public final void L(Configuration configuration) {
        Configuration configuration2 = getConfiguration();
        if (!o20.e(configuration2, configuration)) {
            setConfiguration(new Configuration(configuration));
            if (configuration2.fontScale != configuration.fontScale || configuration2.densityDpi != configuration.densityDpi) {
                setDensity(jc0.b(getContext()));
            }
            if ((configuration2.diff(configuration) & (-1342235264)) != 0) {
                this.t.getClass();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0086  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void M() {
        /*
            Method dump skipped, instructions count: 275
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.b4.M():void");
    }

    public final void N(float f) {
        if (s()) {
            if (f > 0.0f) {
                if (Float.isNaN(this.C0) || f > this.C0) {
                    this.C0 = f;
                    return;
                }
                return;
            }
            if (f < 0.0f) {
                if (Float.isNaN(this.D0) || f < this.D0) {
                    this.D0 = f;
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void addFocusables(ArrayList arrayList, int i, int i2) {
        pt ptVar = ((lt) getFocusOwner()).c;
        if (ptVar.r) {
            if (!ptVar.e.r) {
                q00.b("visitSubtreeIf called on an unattached node");
            }
            ef0 ef0Var = new ef0(new bd0[16]);
            bd0 bd0Var = ptVar.e;
            bd0 bd0Var2 = bd0Var.j;
            if (bd0Var2 == null) {
                k81.f(ef0Var, bd0Var);
            } else {
                ef0Var.b(bd0Var2);
            }
            while (true) {
                int i3 = ef0Var.g;
                if (i3 != 0) {
                    bd0 bd0Var3 = (bd0) ef0Var.k(i3 - 1);
                    if ((bd0Var3.h & 1024) != 0) {
                        for (bd0 bd0Var4 = bd0Var3; bd0Var4 != null && bd0Var4.r; bd0Var4 = bd0Var4.j) {
                            if ((bd0Var4.g & 1024) != 0) {
                                bd0 bd0Var5 = bd0Var4;
                                ef0 ef0Var2 = null;
                                while (bd0Var5 != null) {
                                    int i4 = 0;
                                    if (bd0Var5 instanceof pt) {
                                        pt ptVar2 = (pt) bd0Var5;
                                        if (ptVar2.r && ptVar2.F0().a) {
                                            super.addFocusables(arrayList, i, i2);
                                            pt ptVar3 = ((lt) getFocusOwner()).c;
                                            if (ptVar3.r) {
                                                if (!ptVar3.e.r) {
                                                    q00.b("visitSubtreeIf called on an unattached node");
                                                }
                                                ef0 ef0Var3 = new ef0(new bd0[16]);
                                                bd0 bd0Var6 = ptVar3.e;
                                                bd0 bd0Var7 = bd0Var6.j;
                                                if (bd0Var7 == null) {
                                                    k81.f(ef0Var3, bd0Var6);
                                                } else {
                                                    ef0Var3.b(bd0Var7);
                                                }
                                                while (true) {
                                                    int i5 = ef0Var3.g;
                                                    if (i5 == 0) {
                                                        break;
                                                    }
                                                    bd0 bd0Var8 = (bd0) ef0Var3.k(i5 - 1);
                                                    if ((bd0Var8.h & 1024) != 0) {
                                                        for (bd0 bd0Var9 = bd0Var8; bd0Var9 != null && bd0Var9.r; bd0Var9 = bd0Var9.j) {
                                                            if ((bd0Var9.g & 1024) != 0) {
                                                                bd0 bd0Var10 = bd0Var9;
                                                                ef0 ef0Var4 = null;
                                                                while (bd0Var10 != null) {
                                                                    if (bd0Var10 instanceof pt) {
                                                                        pt ptVar4 = (pt) bd0Var10;
                                                                        if (ptVar4.r) {
                                                                            mt F0 = ptVar4.F0();
                                                                            if (ptVar4.r && F0.a) {
                                                                                return;
                                                                            }
                                                                        }
                                                                    } else if ((bd0Var10.g & 1024) != 0 && (bd0Var10 instanceof jm)) {
                                                                        int i6 = 0;
                                                                        for (bd0 bd0Var11 = ((jm) bd0Var10).t; bd0Var11 != null; bd0Var11 = bd0Var11.j) {
                                                                            if ((bd0Var11.g & 1024) != 0) {
                                                                                i6++;
                                                                                if (i6 == 1) {
                                                                                    bd0Var10 = bd0Var11;
                                                                                } else {
                                                                                    if (ef0Var4 == null) {
                                                                                        ef0Var4 = new ef0(new bd0[16]);
                                                                                    }
                                                                                    if (bd0Var10 != null) {
                                                                                        ef0Var4.b(bd0Var10);
                                                                                        bd0Var10 = null;
                                                                                    }
                                                                                    ef0Var4.b(bd0Var11);
                                                                                }
                                                                            }
                                                                        }
                                                                        if (i6 == 1) {
                                                                        }
                                                                    }
                                                                    bd0Var10 = k81.h(ef0Var4);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    k81.f(ef0Var3, bd0Var8);
                                                }
                                            }
                                            if (arrayList != null) {
                                                arrayList.remove(this);
                                                return;
                                            }
                                            return;
                                        }
                                    } else if ((bd0Var5.g & 1024) != 0 && (bd0Var5 instanceof jm)) {
                                        for (bd0 bd0Var12 = ((jm) bd0Var5).t; bd0Var12 != null; bd0Var12 = bd0Var12.j) {
                                            if ((bd0Var12.g & 1024) != 0) {
                                                i4++;
                                                if (i4 == 1) {
                                                    bd0Var5 = bd0Var12;
                                                } else {
                                                    if (ef0Var2 == null) {
                                                        ef0Var2 = new ef0(new bd0[16]);
                                                    }
                                                    if (bd0Var5 != null) {
                                                        ef0Var2.b(bd0Var5);
                                                        bd0Var5 = null;
                                                    }
                                                    ef0Var2.b(bd0Var12);
                                                }
                                            }
                                        }
                                        if (i4 == 1) {
                                        }
                                    }
                                    bd0Var5 = k81.h(ef0Var2);
                                }
                            }
                        }
                    }
                    k81.f(ef0Var, bd0Var3);
                } else {
                    return;
                }
            }
        }
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i) {
        view.getClass();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = generateDefaultLayoutParams();
        }
        addViewInLayout(view, i, layoutParams, true);
    }

    @Override // android.view.View
    public final void autofill(SparseArray sparseArray) {
        boolean isText;
        boolean isDate;
        boolean isList;
        boolean isToggle;
        CharSequence textValue;
        nu0 u;
        gv gvVar;
        gv gvVar2;
        CharSequence textValue2;
        if (k()) {
            g3 g3Var = this.Q;
            if (g3Var != null) {
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = sparseArray.keyAt(i);
                    AutofillValue h = z0.h(sparseArray.get(keyAt));
                    z40 z40Var = (z40) g3Var.f.c.b(keyAt);
                    if (z40Var != null && (u = z40Var.u()) != null) {
                        ve0 ve0Var = u.e;
                        Object g = ve0Var.g(mu0.g);
                        Object obj = null;
                        if (g == null) {
                            g = null;
                        }
                        n0 n0Var = (n0) g;
                        if (n0Var != null && (gvVar2 = (gv) n0Var.b) != null) {
                            textValue2 = h.getTextValue();
                        }
                        Object g2 = ve0Var.g(mu0.h);
                        if (g2 != null) {
                            obj = g2;
                        }
                        n0 n0Var2 = (n0) obj;
                        if (n0Var2 != null && (gvVar = (gv) n0Var2.b) != null) {
                        }
                    }
                }
            }
            e3 e3Var = this.P;
            if (e3Var != null) {
                u8 u8Var = (u8) e3Var.b;
                if (!u8Var.a.isEmpty()) {
                    int size2 = sparseArray.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        int keyAt2 = sparseArray.keyAt(i2);
                        AutofillValue h2 = z0.h(sparseArray.get(keyAt2));
                        isText = h2.isText();
                        if (isText) {
                            textValue = h2.getTextValue();
                            textValue.toString();
                            if (u8Var.a.get(Integer.valueOf(keyAt2)) != null) {
                                v7.d();
                                return;
                            }
                        } else {
                            isDate = h2.isDate();
                            if (!isDate) {
                                isList = h2.isList();
                                if (!isList) {
                                    isToggle = h2.isToggle();
                                    if (isToggle) {
                                        throw new Error("An operation is not implemented: b/138604541:  Add onFill() callback for toggle");
                                    }
                                } else {
                                    throw new Error("An operation is not implemented: b/138604541: Add onFill() callback for list");
                                }
                            } else {
                                throw new Error("An operation is not implemented: b/138604541: Add onFill() callback for date");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override // defpackage.yl
    public final void b(j80 j80Var) {
        n80 n80Var = this.k;
        if (n80Var != null) {
            ac0 ac0Var = (ac0) n80Var.a.f;
            if (ac0Var.e && !ac0Var.g) {
                rc rcVar = n80Var.d;
                if (rcVar != null) {
                    rcVar.cancel();
                }
                n80Var.d = null;
                return;
            }
            if (!ac0Var.f) {
                if (!ac0Var.g) {
                    dn0.a("ManagedValuesStore tried to leave composition twice. Is the store installed in multiple places?");
                }
                if (!ac0Var.h.i()) {
                    dn0.a("Attempted to start retaining exited values with pending exited values");
                }
                ac0Var.g = false;
            }
        }
    }

    @Override // android.view.View
    public final boolean canScrollHorizontally(int i) {
        return this.D.e(false, i, this.f);
    }

    @Override // android.view.View
    public final boolean canScrollVertically(int i) {
        return this.D.e(true, i, this.f);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        pe0 pe0Var = this.I;
        if (!isAttachedToWindow()) {
            q(getRoot());
        }
        x(true);
        cx0.j().m();
        this.K = true;
        Trace.beginSection("AndroidOwner:draw");
        try {
            zc zcVar = this.w;
            i3 i3Var = zcVar.a;
            Canvas canvas2 = i3Var.a;
            i3Var.a = canvas;
            getRoot().i(i3Var, null);
            zcVar.a.a = canvas2;
            if (pe0Var.i()) {
                int i = pe0Var.b;
                for (int i2 = 0; i2 < i; i2++) {
                    ((kx) ((lj0) pe0Var.f(i2))).g();
                }
            }
            int i3 = p51.e;
            pe0Var.d();
            this.K = false;
            Trace.endSection();
            pe0 pe0Var2 = this.J;
            if (pe0Var2 != null) {
                pe0Var.b(pe0Var2);
                pe0Var2.d();
            }
            if (s()) {
                q7.a(this, this.C0);
                View view = this.p;
                if (view != null) {
                    q7.a(view, this.D0);
                    if (!Float.isNaN(this.D0)) {
                        view.invalidate();
                        drawChild(canvas, view, getDrawingTime());
                    }
                }
                this.C0 = Float.NaN;
                this.D0 = Float.NaN;
            }
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:665:0x0456, code lost:
    
        if ((r2 / r3) >= 5.0f) goto L262;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11, types: [bd0] */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v17, types: [e01] */
    /* JADX WARN: Type inference failed for: r0v32, types: [bd0] */
    /* JADX WARN: Type inference failed for: r0v33 */
    /* JADX WARN: Type inference failed for: r0v37 */
    /* JADX WARN: Type inference failed for: r0v38 */
    /* JADX WARN: Type inference failed for: r0v40, types: [e01] */
    /* JADX WARN: Type inference failed for: r2v45, types: [bd0] */
    /* JADX WARN: Type inference failed for: r2v46 */
    /* JADX WARN: Type inference failed for: r2v47 */
    /* JADX WARN: Type inference failed for: r2v48 */
    /* JADX WARN: Type inference failed for: r2v50, types: [e01] */
    /* JADX WARN: Type inference failed for: r2v78 */
    /* JADX WARN: Type inference failed for: r2v79 */
    /* JADX WARN: Type inference failed for: r2v81, types: [e01] */
    /* JADX WARN: Type inference failed for: r33v0 */
    /* JADX WARN: Type inference failed for: r33v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r33v2 */
    /* JADX WARN: Type inference failed for: r39v0 */
    /* JADX WARN: Type inference failed for: r39v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r39v2 */
    /* JADX WARN: Type inference failed for: r3v29 */
    /* JADX WARN: Type inference failed for: r3v30, types: [im, k00] */
    /* JADX WARN: Type inference failed for: r3v33 */
    /* JADX WARN: Type inference failed for: r3v34 */
    /* JADX WARN: Type inference failed for: r3v42 */
    /* JADX WARN: Type inference failed for: r3v43, types: [bd0] */
    /* JADX WARN: Type inference failed for: r3v7, types: [p5, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v83 */
    /* JADX WARN: Type inference failed for: r3v86 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18, types: [java.util.List, java.util.Collection] */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v22 */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v28, types: [ef0] */
    /* JADX WARN: Type inference failed for: r4v29 */
    /* JADX WARN: Type inference failed for: r4v30 */
    /* JADX WARN: Type inference failed for: r4v31 */
    /* JADX WARN: Type inference failed for: r4v32, types: [ef0] */
    /* JADX WARN: Type inference failed for: r4v34 */
    /* JADX WARN: Type inference failed for: r4v35, types: [im, k00] */
    /* JADX WARN: Type inference failed for: r4v39 */
    /* JADX WARN: Type inference failed for: r4v40 */
    /* JADX WARN: Type inference failed for: r4v48 */
    /* JADX WARN: Type inference failed for: r4v49, types: [bd0] */
    /* JADX WARN: Type inference failed for: r4v58 */
    /* JADX WARN: Type inference failed for: r4v61 */
    /* JADX WARN: Type inference failed for: r4v63 */
    /* JADX WARN: Type inference failed for: r4v64 */
    /* JADX WARN: Type inference failed for: r4v65 */
    /* JADX WARN: Type inference failed for: r4v66 */
    /* JADX WARN: Type inference failed for: r4v67 */
    /* JADX WARN: Type inference failed for: r4v68 */
    /* JADX WARN: Type inference failed for: r4v69 */
    /* JADX WARN: Type inference failed for: r4v70 */
    /* JADX WARN: Type inference failed for: r4v71 */
    /* JADX WARN: Type inference failed for: r4v74 */
    /* JADX WARN: Type inference failed for: r4v75 */
    /* JADX WARN: Type inference failed for: r5v32 */
    /* JADX WARN: Type inference failed for: r5v33, types: [java.lang.Object, bd0] */
    /* JADX WARN: Type inference failed for: r5v34 */
    /* JADX WARN: Type inference failed for: r5v35, types: [bd0] */
    /* JADX WARN: Type inference failed for: r5v36, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v37 */
    /* JADX WARN: Type inference failed for: r5v38 */
    /* JADX WARN: Type inference failed for: r5v39 */
    /* JADX WARN: Type inference failed for: r5v40 */
    /* JADX WARN: Type inference failed for: r5v41 */
    /* JADX WARN: Type inference failed for: r5v54 */
    /* JADX WARN: Type inference failed for: r5v55, types: [java.util.List, java.util.Collection] */
    /* JADX WARN: Type inference failed for: r5v56 */
    /* JADX WARN: Type inference failed for: r5v57 */
    /* JADX WARN: Type inference failed for: r5v58 */
    /* JADX WARN: Type inference failed for: r5v59 */
    /* JADX WARN: Type inference failed for: r5v60 */
    /* JADX WARN: Type inference failed for: r5v65, types: [ef0] */
    /* JADX WARN: Type inference failed for: r5v66 */
    /* JADX WARN: Type inference failed for: r5v67 */
    /* JADX WARN: Type inference failed for: r5v68 */
    /* JADX WARN: Type inference failed for: r5v69, types: [ef0] */
    /* JADX WARN: Type inference failed for: r5v82 */
    /* JADX WARN: Type inference failed for: r5v83 */
    /* JADX WARN: Type inference failed for: r5v84 */
    /* JADX WARN: Type inference failed for: r5v85 */
    /* JADX WARN: Type inference failed for: r5v86 */
    /* JADX WARN: Type inference failed for: r5v87 */
    /* JADX WARN: Type inference failed for: r5v88 */
    /* JADX WARN: Type inference failed for: r5v89 */
    /* JADX WARN: Type inference failed for: r5v90 */
    /* JADX WARN: Type inference failed for: r5v93 */
    /* JADX WARN: Type inference failed for: r5v94 */
    /* JADX WARN: Type inference failed for: r5v95 */
    /* JADX WARN: Type inference failed for: r5v96 */
    /* JADX WARN: Type inference failed for: r5v97 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15, types: [ef0] */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v18, types: [ef0] */
    /* JADX WARN: Type inference failed for: r6v35 */
    /* JADX WARN: Type inference failed for: r6v36, types: [java.lang.Object, bd0] */
    /* JADX WARN: Type inference failed for: r6v37 */
    /* JADX WARN: Type inference failed for: r6v38, types: [bd0] */
    /* JADX WARN: Type inference failed for: r6v39, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v40 */
    /* JADX WARN: Type inference failed for: r6v41 */
    /* JADX WARN: Type inference failed for: r6v42 */
    /* JADX WARN: Type inference failed for: r6v43 */
    /* JADX WARN: Type inference failed for: r6v65 */
    /* JADX WARN: Type inference failed for: r6v66 */
    /* JADX WARN: Type inference failed for: r6v67 */
    /* JADX WARN: Type inference failed for: r6v68 */
    /* JADX WARN: Type inference failed for: r6v69 */
    /* JADX WARN: Type inference failed for: r6v70 */
    /* JADX WARN: Type inference failed for: r7v22 */
    /* JADX WARN: Type inference failed for: r7v23 */
    /* JADX WARN: Type inference failed for: r7v24 */
    /* JADX WARN: Type inference failed for: r7v25, types: [ef0] */
    /* JADX WARN: Type inference failed for: r7v26 */
    /* JADX WARN: Type inference failed for: r7v27 */
    /* JADX WARN: Type inference failed for: r7v28, types: [ef0] */
    /* JADX WARN: Type inference failed for: r7v34 */
    /* JADX WARN: Type inference failed for: r7v35 */
    /* JADX WARN: Type inference failed for: r7v36 */
    /* JADX WARN: Type inference failed for: r7v37 */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean dispatchGenericMotionEvent(android.view.MotionEvent r44) {
        /*
            Method dump skipped, instructions count: 2031
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.b4.dispatchGenericMotionEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x0154, code lost:
    
        if (v(r24) == false) goto L71;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean dispatchHoverEvent(android.view.MotionEvent r24) {
        /*
            Method dump skipped, instructions count: 352
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.b4.dispatchHoverEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int i = 0;
        if (isFocused()) {
            t70 t70Var = getComposeViewContext().s;
            int metaState = keyEvent.getMetaState();
            t70Var.getClass();
            e61.a.setValue(new zm0(metaState));
            if (!((lt) getFocusOwner()).d(keyEvent, di.y) && !super.dispatchKeyEvent(keyEvent)) {
                return false;
            }
            return true;
        }
        return ((lt) getFocusOwner()).d(keyEvent, new u3(i, this, keyEvent));
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        lg0 lg0Var;
        if (isFocused()) {
            lt ltVar = (lt) getFocusOwner();
            if (ltVar.d.e) {
                System.out.println((Object) "FocusRelatedWarning: Dispatching intercepted soft keyboard event while the focus system is invalidated.");
            } else {
                pt s = n20.s(ltVar.c);
                if (s != null) {
                    if (!s.e.r) {
                        q00.b("visitAncestors called on an unattached node");
                    }
                    bd0 bd0Var = s.e;
                    z40 E = k81.E(s);
                    while (E != null) {
                        if ((E.H.f.h & 131072) != 0) {
                            while (bd0Var != null) {
                                if ((bd0Var.g & 131072) != 0) {
                                    bd0 bd0Var2 = bd0Var;
                                    ef0 ef0Var = null;
                                    while (bd0Var2 != null) {
                                        if ((bd0Var2.g & 131072) != 0 && (bd0Var2 instanceof jm)) {
                                            int i = 0;
                                            for (bd0 bd0Var3 = ((jm) bd0Var2).t; bd0Var3 != null; bd0Var3 = bd0Var3.j) {
                                                if ((bd0Var3.g & 131072) != 0) {
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
            }
        }
        if (!super.dispatchKeyEventPreIme(keyEvent)) {
            return false;
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchProvideStructure(ViewStructure viewStructure) {
        if (Build.VERSION.SDK_INT < 28) {
            i4.a.a(viewStructure, getView());
        } else {
            super.dispatchProvideStructure(viewStructure);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        View view;
        Object p8Var;
        pt f;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        if (this.G0) {
            m3 m3Var = this.F0;
            removeCallbacks(m3Var);
            MotionEvent motionEvent2 = this.y0;
            motionEvent2.getClass();
            if (motionEvent.getActionMasked() == 0 && motionEvent2.getSource() == motionEvent.getSource() && motionEvent2.getToolType(0) == motionEvent.getToolType(0)) {
                this.G0 = false;
            } else {
                m3Var.run();
            }
        }
        if (!t(motionEvent) && isAttachedToWindow() && (motionEvent.getActionMasked() != 2 || v(motionEvent))) {
            int p = p(motionEvent);
            if ((p & 2) != 0) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            if (motionEvent.getActionMasked() != 0 && motionEvent.getActionMasked() != 5) {
                z = false;
            } else {
                z = true;
            }
            if (!motionEvent.isFromSource(8194) && !motionEvent.isFromSource(1048584)) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z && z2) {
                Object parent = getParent();
                if (parent instanceof View) {
                    view = (View) parent;
                } else {
                    view = null;
                }
                if (view == null || (p8Var = view.getTag(R.id.auto_clear_focus_behavior_tag)) == null) {
                    p8Var = new p8(1);
                }
                if (p8Var.equals(new p8(1)) && (f = ((lt) getFocusOwner()).f()) != null) {
                    ng0 D = k81.D(f);
                    wo0 U = o30.n(D).U(D, true);
                    long floatToRawIntBits = (Float.floatToRawIntBits(motionEvent.getX()) << 32) | (Float.floatToRawIntBits(motionEvent.getY()) & 4294967295L);
                    float intBitsToFloat = Float.intBitsToFloat((int) (floatToRawIntBits >> 32));
                    float intBitsToFloat2 = Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L));
                    if (intBitsToFloat >= U.a) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (intBitsToFloat < U.c) {
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    boolean z7 = z4 & z3;
                    if (intBitsToFloat2 >= U.b) {
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    boolean z8 = z7 & z5;
                    if (intBitsToFloat2 < U.d) {
                        z6 = true;
                    } else {
                        z6 = false;
                    }
                    if (!(z8 & z6)) {
                        ((lt) getFocusOwner()).b(8, false, true);
                    }
                }
            }
            if ((p & 1) != 0) {
                return true;
            }
        }
        return false;
    }

    @Override // defpackage.yl
    public final void e(j80 j80Var) {
        rc rcVar;
        if (Build.VERSION.SDK_INT < 30) {
            setShowLayoutBounds(k81.v());
        }
        n80 n80Var = this.k;
        if (n80Var != null) {
            m80 m80Var = this.j;
            m80Var.getClass();
            j2 j2Var = n80Var.a;
            ac0 ac0Var = (ac0) j2Var.f;
            if (ac0Var.e && !ac0Var.g) {
                try {
                    rcVar = ((g81) m80Var).a.s(new n9(11, n80Var));
                } catch (CancellationException unused) {
                    ac0 ac0Var2 = (ac0) j2Var.f;
                    if (!ac0Var2.f) {
                        if (ac0Var2.g) {
                            dn0.a("ManagedValuesStore tried to enter composition twice. Did you attempt to install the same store multiple times or into two compositions?");
                        }
                        ac0Var2.a();
                        ac0Var2.g = true;
                    }
                    rcVar = null;
                }
                rc rcVar2 = n80Var.d;
                if (rcVar2 != null) {
                    rcVar2.cancel();
                }
                n80Var.d = rcVar;
            }
        }
    }

    public final View findViewByAccessibilityIdTraversal(int i) {
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                Method declaredMethod = View.class.getDeclaredMethod("findViewByAccessibilityIdTraversal", Integer.TYPE);
                declaredMethod.setAccessible(true);
                Object invoke = declaredMethod.invoke(this, Integer.valueOf(i));
                if (invoke instanceof View) {
                    return (View) invoke;
                }
                return null;
            }
            return n(this, i);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [ep0, java.lang.Object] */
    @Override // android.view.ViewGroup, android.view.ViewParent
    public final View focusSearch(View view, int i) {
        int i2;
        if (view != null && !this.c0.c) {
            View rootView = getRootView();
            rootView.getClass();
            View findNextFocus = FocusFinder.getInstance().findNextFocus((ViewGroup) rootView, view, i);
            wo0 wo0Var = null;
            if (findNextFocus != null && !findNextFocus.equals(this)) {
                for (ViewParent parent = findNextFocus.getParent(); parent != null; parent = parent.getParent()) {
                    if (parent == this) {
                        break;
                    }
                }
            }
            findNextFocus = null;
            if (view == this) {
                pt s = n20.s(((lt) getFocusOwner()).c);
                if (s != null) {
                    wo0Var = n20.t(s);
                }
                if (wo0Var == null) {
                    wo0Var = et.a(view, this);
                }
            } else {
                wo0Var = et.a(view, this);
            }
            bt b = et.b(i);
            if (b != null) {
                i2 = b.a;
            } else {
                i2 = 6;
            }
            ?? obj = new Object();
            if (((lt) getFocusOwner()).e(i2, wo0Var, new v3(0, obj)) == null) {
                return view;
            }
            Object obj2 = obj.e;
            if (obj2 == null) {
                if (findNextFocus == null) {
                    return super.focusSearch(view, i);
                }
            } else if (findNextFocus == null || i2 == 1 || i2 == 2 || t20.x(n20.t((pt) obj2), et.a(findNextFocus, this), wo0Var, i2)) {
                return this;
            }
            return findNextFocus;
        }
        return super.focusSearch(view, i);
    }

    @Override // defpackage.gt
    public final void g(pt ptVar, pt ptVar2) {
        boolean z;
        lg0 lg0Var;
        boolean z2;
        lg0 lg0Var2;
        boolean z3;
        if (ptVar != null) {
            if (!ptVar.e.r) {
                q00.b("visitAncestors called on an unattached node");
            }
            bd0 bd0Var = ptVar.e;
            z40 E = k81.E(ptVar);
            we0 we0Var = null;
            ArrayList arrayList = null;
            while (E != null) {
                if ((E.H.f.h & 2097152) != 0) {
                    while (bd0Var != null) {
                        if ((bd0Var.g & 2097152) != 0) {
                            bd0 bd0Var2 = bd0Var;
                            ef0 ef0Var = null;
                            while (bd0Var2 != null) {
                                if (bd0Var2 instanceof k00) {
                                    if (arrayList == null) {
                                        arrayList = new ArrayList();
                                    }
                                    arrayList.add(bd0Var2);
                                    z3 = false;
                                } else {
                                    z3 = true;
                                }
                                if (z3 && (bd0Var2.g & 2097152) != 0 && (bd0Var2 instanceof jm)) {
                                    int i = 0;
                                    for (bd0 bd0Var3 = ((jm) bd0Var2).t; bd0Var3 != null; bd0Var3 = bd0Var3.j) {
                                        if ((bd0Var3.g & 2097152) != 0) {
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
                if (E != null && (lg0Var2 = E.H) != null) {
                    bd0Var = lg0Var2.e;
                } else {
                    bd0Var = null;
                }
            }
            if (arrayList != null) {
                if (ptVar2 != null) {
                    if (!ptVar2.e.r) {
                        q00.b("visitAncestors called on an unattached node");
                    }
                    bd0 bd0Var4 = ptVar2.e;
                    z40 E2 = k81.E(ptVar2);
                    we0 we0Var2 = null;
                    while (E2 != null) {
                        if ((E2.H.f.h & 2097152) != 0) {
                            while (bd0Var4 != null) {
                                if ((bd0Var4.g & 2097152) != 0) {
                                    bd0 bd0Var5 = bd0Var4;
                                    ef0 ef0Var2 = null;
                                    while (bd0Var5 != null) {
                                        if (bd0Var5 instanceof k00) {
                                            if (we0Var2 == null) {
                                                we0 we0Var3 = at0.a;
                                                we0Var2 = new we0();
                                            }
                                            we0Var2.a(bd0Var5);
                                            z2 = false;
                                        } else {
                                            z2 = true;
                                        }
                                        if (z2 && (bd0Var5.g & 2097152) != 0 && (bd0Var5 instanceof jm)) {
                                            int i2 = 0;
                                            for (bd0 bd0Var6 = ((jm) bd0Var5).t; bd0Var6 != null; bd0Var6 = bd0Var6.j) {
                                                if ((bd0Var6.g & 2097152) != 0) {
                                                    i2++;
                                                    if (i2 == 1) {
                                                        bd0Var5 = bd0Var6;
                                                    } else {
                                                        if (ef0Var2 == null) {
                                                            ef0Var2 = new ef0(new bd0[16]);
                                                        }
                                                        if (bd0Var5 != null) {
                                                            ef0Var2.b(bd0Var5);
                                                            bd0Var5 = null;
                                                        }
                                                        ef0Var2.b(bd0Var6);
                                                    }
                                                }
                                            }
                                            if (i2 == 1) {
                                            }
                                        }
                                        bd0Var5 = k81.h(ef0Var2);
                                    }
                                }
                                bd0Var4 = bd0Var4.i;
                            }
                        }
                        E2 = E2.s();
                        if (E2 != null && (lg0Var = E2.H) != null) {
                            bd0Var4 = lg0Var.e;
                        } else {
                            bd0Var4 = null;
                        }
                    }
                    we0Var = we0Var2;
                }
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    k00 k00Var = (k00) arrayList.get(i3);
                    if (we0Var != null) {
                        z = we0Var.c(k00Var);
                    } else {
                        z = false;
                    }
                    if (!z) {
                        k00Var.I();
                    }
                }
            }
        }
    }

    public final t6 getAndroidViewsHandler$ui() {
        if (this.W == null) {
            t6 t6Var = new t6(getContext());
            this.W = t6Var;
            addView(t6Var, -1);
            requestLayout();
        }
        t6 t6Var2 = this.W;
        t6Var2.getClass();
        return t6Var2;
    }

    public q8 getAutofill() {
        return this.P;
    }

    public t8 getAutofillManager() {
        return this.Q;
    }

    public u8 getAutofillTree() {
        return this.H;
    }

    public final nh getComposeViewContext() {
        return get_composeViewContext();
    }

    public final boolean getComposeViewContextIncrementedDuringInit$ui() {
        return this.L0;
    }

    public final Configuration getConfiguration() {
        return (Configuration) this.N.getValue();
    }

    public final t4 getContentCaptureManager$ui() {
        return this.E;
    }

    public yj getCoroutineContext() {
        return this.r;
    }

    public mm getDensity() {
        return (mm) this.o.getValue();
    }

    public wo0 getEmbeddedViewFocusRect() {
        if (isFocused()) {
            pt s = n20.s(((lt) getFocusOwner()).c);
            if (s == null) {
                return null;
            }
            return n20.t(s);
        }
        View findFocus = findFocus();
        if (findFocus == null) {
            return null;
        }
        return et.a(findFocus, this);
    }

    public ht getFocusOwner() {
        return this.q;
    }

    @Override // android.view.View
    public final void getFocusedRect(Rect rect) {
        wo0 embeddedViewFocusRect = getEmbeddedViewFocusRect();
        if (embeddedViewFocusRect != null) {
            rect.left = Math.round(embeddedViewFocusRect.a);
            rect.top = Math.round(embeddedViewFocusRect.b);
            rect.right = Math.round(embeddedViewFocusRect.c);
            rect.bottom = Math.round(embeddedViewFocusRect.d);
            return;
        }
        if (!o20.e(((lt) getFocusOwner()).e(6, null, w3.g), Boolean.TRUE)) {
            rect.set(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        } else {
            super.getFocusedRect(rect);
        }
    }

    public wt getFontFamilyResolver() {
        return (wt) this.s0.getValue();
    }

    public vt getFontLoader() {
        return this.r0;
    }

    public final m80 getFrameEndScheduler$ui() {
        return this.j;
    }

    public ex getGraphicsContext() {
        return this.G;
    }

    public ay getHapticFeedBack() {
        return this.u0;
    }

    public boolean getHasPendingMeasureOrLayout() {
        if (!this.c0.b.x() && this.m.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override // android.view.View
    public int getImportantForAutofill() {
        return 1;
    }

    public e10 getInputModeManager() {
        return this.v0;
    }

    public final j10 getInsetsListener() {
        return this.y;
    }

    public final long getLastMatrixRecalculationAnimationTime$ui() {
        return this.h0;
    }

    @Override // android.view.View, android.view.ViewParent
    public m40 getLayoutDirection() {
        return (m40) this.t0.getValue();
    }

    public ua0 getLocaleList() {
        return (ua0) this.O.getValue();
    }

    public long getMeasureIteration() {
        mc0 mc0Var = this.c0;
        if (!mc0Var.c) {
            q00.a("measureIteration should be only used during the measure/layout pass");
        }
        return mc0Var.g;
    }

    public dd0 getModifierLocalManager() {
        return this.w0;
    }

    /* renamed from: getOutOfFrameExecutor, reason: merged with bridge method [inline-methods] */
    public b4 m8getOutOfFrameExecutor() {
        if (isAttachedToWindow()) {
            return this;
        }
        return null;
    }

    public dm0 getPlacementScope() {
        int i = fm0.b;
        return new pb0(1, this);
    }

    public tm0 getPointerIconService() {
        return this.O0;
    }

    /* renamed from: getPrimaryDirectionalMotionAxisOverride-dqNNBbU$ui, reason: not valid java name */
    public final b00 m1getPrimaryDirectionalMotionAxisOverridedqNNBbU$ui() {
        return this.h;
    }

    public yo0 getRectManager() {
        return this.B;
    }

    public nq0 getRetainedValuesStore() {
        return this.l;
    }

    public z40 getRoot() {
        return this.z;
    }

    public final boolean getScrollCaptureInProgress$ui() {
        t70 t70Var;
        if (Build.VERSION.SDK_INT >= 31 && (t70Var = this.M0) != null) {
            return ((Boolean) t70Var.a.getValue()).booleanValue();
        }
        return false;
    }

    public vu0 getSemanticsOwner() {
        return this.C;
    }

    public b50 getSharedDrawScope() {
        return this.i;
    }

    public boolean getShowLayoutBounds() {
        if (Build.VERSION.SDK_INT >= 30) {
            return o7.a.a(this);
        }
        return this.V;
    }

    public pj0 getSnapshotObserver() {
        return this.U;
    }

    public px0 getSoftwareKeyboardController() {
        dt0 dt0Var = this.q0;
        if (dt0Var == null) {
            getTextInputService();
            dt0 dt0Var2 = new dt0(26);
            this.q0 = dt0Var2;
            return dt0Var2;
        }
        return dt0Var;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [d11, java.lang.Object] */
    public d11 getTextInputService() {
        d11 d11Var = this.o0;
        if (d11Var == null) {
            getLegacyTextInputServiceAndroid();
            ?? obj = new Object();
            new AtomicReference(null);
            this.o0 = obj;
            return obj;
        }
        return d11Var;
    }

    public s11 getTextToolbar() {
        return this.x0;
    }

    public final dr0 getUncaughtExceptionHandler$ui() {
        return null;
    }

    public l51 getViewConfiguration() {
        return this.x;
    }

    public final r3 getViewTreeOwners() {
        d3.z(this.l0.getValue());
        return null;
    }

    public d61 getWindowInfo() {
        return getComposeViewContext().s;
    }

    public final g3 get_autofillManager$ui() {
        return this.Q;
    }

    public final void o(z40 z40Var, boolean z) {
        this.c0.g(z40Var, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v38, types: [o3, java.lang.Object] */
    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        nq0 nq0Var;
        Object obj;
        e3 e3Var;
        Method method;
        super.onAttachedToWindow();
        int i = 1;
        setAttached(true);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 30) {
            setShowLayoutBounds(k81.v());
        }
        this.y.onViewAttachedToWindow(this);
        int i3 = 0;
        if (i2 > 28) {
            if (T0 == null) {
                ?? obj2 = new Object();
                T0 = obj2;
                StrictMode.VmPolicy vmPolicy = StrictMode.getVmPolicy();
                try {
                    if (P0 == null) {
                        P0 = Class.forName("android.os.SystemProperties");
                    }
                    if (R0 == null) {
                        StrictMode.setVmPolicy(StrictMode.VmPolicy.LAX);
                        Class cls = P0;
                        if (cls != null) {
                            method = cls.getDeclaredMethod("addChangeCallback", Runnable.class);
                        } else {
                            method = null;
                        }
                        R0 = method;
                    }
                    Method method2 = R0;
                    if (method2 != null) {
                        method2.invoke(null, obj2);
                    }
                } catch (Throwable unused) {
                }
                StrictMode.setVmPolicy(vmPolicy);
            }
            pe0 pe0Var = S0;
            synchronized (pe0Var) {
                pe0Var.a(this);
            }
        }
        if (!this.L0) {
            getComposeViewContext().c();
        }
        this.L0 = false;
        r(getRoot());
        q(getRoot());
        getSnapshotObserver().a();
        if (k() && (e3Var = this.P) != null) {
            s8 s8Var = s8.a;
            s8Var.getClass();
            ((AutofillManager) e3Var.c).registerCallback(z0.f(s8Var));
        }
        j80 j80Var = getComposeViewContext().c;
        w51 w51Var = getComposeViewContext().e;
        m80 m80Var = this.j;
        if (j80Var != null && w51Var != null && m80Var != null) {
            wb0 c = ((cg) w51Var).c();
            ey0 ey0Var = new ey0(12);
            mk mkVar = mk.b;
            mkVar.getClass();
            e3 e3Var2 = new e3(c, ey0Var, mkVar);
            wd a = fp0.a(o80.class);
            String a2 = a.a();
            if (a2 != null) {
                o80 o80Var = (o80) e3Var2.o(a, "androidx.lifecycle.ViewModelProvider.DefaultKey:".concat(a2));
                Object parent = getParent();
                parent.getClass();
                int id = ((View) parent).getId();
                he0 he0Var = o80Var.b;
                Object b = he0Var.b(id);
                if (b == null) {
                    b = new pe0(1);
                    he0Var.h(id, b);
                }
                pe0 pe0Var2 = (pe0) b;
                Object[] objArr = pe0Var2.a;
                int i4 = pe0Var2.b;
                while (true) {
                    if (i3 < i4) {
                        obj = objArr[i3];
                        if (!((n80) obj).c) {
                            break;
                        } else {
                            i3++;
                        }
                    } else {
                        obj = null;
                        break;
                    }
                }
                n80 n80Var = (n80) obj;
                if (n80Var == null) {
                    n80Var = new n80();
                    pe0Var2.a(n80Var);
                }
                n80Var.c = true;
                this.k = n80Var;
                nq0Var = n80Var.b;
            } else {
                v7.m("Local and anonymous classes can not be ViewModels");
                return;
            }
        } else {
            nq0Var = null;
        }
        if (nq0Var == null) {
            nq0Var = x1.I;
        }
        this.l = nq0Var;
        gv gvVar = this.m0;
        if (gvVar != null) {
            gvVar.e(getComposeViewContext());
            this.m0 = null;
        }
        l80 f = getComposeViewContext().c.f();
        f.a(this);
        f.a(this.E);
        f10 f10Var = this.v0;
        if (!isInTouchMode()) {
            i = 2;
        }
        f10Var.a.setValue(new d10(i));
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        getViewTreeObserver().addOnScrollChangedListener(this);
        getViewTreeObserver().addOnTouchModeChangeListener(this);
        if (Build.VERSION.SDK_INT >= 31) {
            m4.a.b(this);
        }
        g3 g3Var = this.Q;
        if (g3Var != null) {
            ((lt) getFocusOwner()).g.a(g3Var);
            getSemanticsOwner().d.a(g3Var);
        }
        ((lt) getFocusOwner()).g.a(this);
    }

    @Override // android.view.View
    public final boolean onCheckIsTextEditor() {
        if (this.p0.get() == null) {
            getLegacyTextInputServiceAndroid().getClass();
            return false;
        }
        v7.d();
        return false;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        L(configuration);
    }

    @Override // android.view.View
    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        if (this.p0.get() == null) {
            getLegacyTextInputServiceAndroid().getClass();
            return null;
        }
        v7.d();
        return null;
    }

    @Override // android.view.View
    public final void onCreateVirtualViewTranslationRequests(long[] jArr, int[] iArr, Consumer consumer) {
        su0 su0Var;
        AutofillId autofillId;
        t4 t4Var = this.E;
        t4Var.getClass();
        for (long j : jArr) {
            uu0 uu0Var = (uu0) t4Var.j().b((int) j);
            if (uu0Var != null && (su0Var = uu0Var.a) != null) {
                l4.t();
                autofillId = t4Var.e.getAutofillId();
                ViewTranslationRequest.Builder p = l4.p(autofillId, su0Var.f);
                Object g = su0Var.d.e.g(wu0.z);
                if (g == null) {
                    g = null;
                }
                List list = (List) g;
                if (list != null) {
                    l4.B(p, l4.n(new l7(ma0.a(list, "\n", null, 62))));
                    consumer.accept(l4.q(p));
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        e3 e3Var;
        super.onDetachedFromWindow();
        setAttached(false);
        this.y.onViewDetachedFromWindow(this);
        View view = this.p;
        if (s() && view != null) {
            removeView(view);
        }
        int i = Build.VERSION.SDK_INT;
        if (i > 28) {
            pe0 pe0Var = S0;
            synchronized (pe0Var) {
                pe0Var.j(this);
            }
        }
        getComposeViewContext().b();
        getSnapshotObserver().b();
        l80 f = getComposeViewContext().c.f();
        f.f(this.E);
        f.f(this);
        if (k() && (e3Var = this.P) != null) {
            s8 s8Var = s8.a;
            s8Var.getClass();
            ((AutofillManager) e3Var.c).unregisterCallback(z0.f(s8Var));
        }
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        getViewTreeObserver().removeOnScrollChangedListener(this);
        getViewTreeObserver().removeOnTouchModeChangeListener(this);
        n80 n80Var = this.k;
        if (n80Var != null) {
            n80Var.c = false;
        }
        this.k = null;
        if (i >= 31) {
            m4.a.a(this);
        }
        g3 g3Var = this.Q;
        if (g3Var != null) {
            getSemanticsOwner().d.j(g3Var);
            ((lt) getFocusOwner()).g.j(g3Var);
        }
        yo0 rectManager = getRectManager();
        rectManager.f = rectManager.c.b(0L, 0L, null, 0, 0);
        getRectManager().a();
        yo0 rectManager2 = getRectManager();
        n3 n3Var = rectManager2.h;
        if (n3Var != null) {
            b4 b4Var = rectManager2.a;
            if (!d3.A(n3Var)) {
                n3Var = null;
            }
            if (n3Var != null) {
                b4Var.removeCallbacks(n3Var);
            }
            rectManager2.h = null;
        }
        ((lt) getFocusOwner()).g.j(this);
    }

    @Override // android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (!z && !hasFocus()) {
            lt ltVar = (lt) getFocusOwner();
            dl.o(ltVar.c, true);
            if (ltVar.f() != null) {
                pt f = ltVar.f();
                ltVar.h(null);
                if (f != null) {
                    f.E0(ot.e, ot.g);
                }
            }
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        this.h0 = 0L;
        M();
        int i = Build.VERSION.SDK_INT;
        if (32 <= i && i < 34) {
            L(getResources().getConfiguration());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Trace.beginSection("AndroidOwner:onLayout");
        try {
            this.h0 = 0L;
            this.c0.l(this.I0);
            this.a0 = null;
            M();
            if (this.W != null) {
                Trace.beginSection("AndroidOwner:viewLayout");
                getAndroidViewsHandler$ui().layout(0, 0, i3 - i, i4 - i2);
                Trace.endSection();
            }
        } catch (Throwable th) {
            throw th;
        } finally {
            Trace.endSection();
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        mc0 mc0Var = this.c0;
        Trace.beginSection("AndroidOwner:onMeasure");
        try {
            if (!isAttachedToWindow()) {
                r(getRoot());
            }
            long m = m(i);
            long m2 = m(i2);
            long x = f31.x((int) (m >>> 32), (int) (m & 4294967295L), (int) (m2 >>> 32), (int) (4294967295L & m2));
            si siVar = this.a0;
            if (siVar == null) {
                this.a0 = new si(x);
                this.b0 = false;
            } else if (!si.b(siVar.a, x)) {
                this.b0 = true;
            }
            mc0Var.s(x);
            mc0Var.n();
            setMeasuredDimension(getRoot().I.p.e, getRoot().I.p.f);
            if (this.W != null) {
                Trace.beginSection("AndroidOwner:androidViewMeasure");
                getAndroidViewsHandler$ui().measure(View.MeasureSpec.makeMeasureSpec(getRoot().I.p.e, 1073741824), View.MeasureSpec.makeMeasureSpec(getRoot().I.p.f, 1073741824));
                Trace.endSection();
            }
        } catch (Throwable th) {
            throw th;
        } finally {
            Trace.endSection();
        }
    }

    @Override // android.view.View
    public final void onProvideAutofillVirtualStructure(ViewStructure viewStructure, int i) {
        if (k() && viewStructure != null) {
            g3 g3Var = this.Q;
            if (g3Var != null) {
                z40 z40Var = g3Var.f.a;
                AutofillId autofillId = g3Var.j;
                String str = g3Var.i;
                yo0 yo0Var = g3Var.h;
                d20.E(viewStructure, z40Var, autofillId, str, yo0Var);
                Object[] objArr = yg0.a;
                pe0 pe0Var = new pe0(2);
                pe0Var.a(z40Var);
                pe0Var.a(viewStructure);
                while (pe0Var.i()) {
                    Object k = pe0Var.k(pe0Var.b - 1);
                    k.getClass();
                    ViewStructure viewStructure2 = (ViewStructure) k;
                    Object k2 = pe0Var.k(pe0Var.b - 1);
                    k2.getClass();
                    bf0 bf0Var = (bf0) ((z40) k2).m();
                    int i2 = bf0Var.e.g;
                    for (int i3 = 0; i3 < i2; i3++) {
                        z40 z40Var2 = (z40) bf0Var.get(i3);
                        if (!z40Var2.Q && z40Var2.E() && z40Var2.F()) {
                            nu0 u = z40Var2.u();
                            if (u != null) {
                                ve0 ve0Var = u.e;
                                if (ve0Var.b(mu0.g) || ve0Var.b(mu0.h) || ve0Var.b(wu0.q) || ve0Var.b(wu0.r)) {
                                    ViewStructure newChild = viewStructure2.newChild(viewStructure2.addChildCount(1));
                                    d20.E(newChild, z40Var2, g3Var.j, str, yo0Var);
                                    pe0Var.a(z40Var2);
                                    pe0Var.a(newChild);
                                }
                            }
                            pe0Var.a(z40Var2);
                            pe0Var.a(viewStructure2);
                        }
                    }
                }
            }
            e3 e3Var = this.P;
            if (e3Var != null) {
                u8 u8Var = (u8) e3Var.b;
                LinkedHashMap linkedHashMap = u8Var.a;
                LinkedHashMap linkedHashMap2 = u8Var.a;
                if (!linkedHashMap.isEmpty()) {
                    int addChildCount = viewStructure.addChildCount(linkedHashMap2.size());
                    Iterator it = linkedHashMap2.entrySet().iterator();
                    if (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        int intValue = ((Number) entry.getKey()).intValue();
                        if (entry.getValue() != null) {
                            v7.d();
                            return;
                        }
                        ViewStructure newChild2 = viewStructure.newChild(addChildCount);
                        newChild2.setAutofillId((AutofillId) e3Var.d, intValue);
                        newChild2.setId(intValue, ((b4) e3Var.a).getContext().getPackageName(), null, null);
                        newChild2.setAutofillType(1);
                        throw null;
                    }
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        int toolType = motionEvent.getToolType(i);
        if (!motionEvent.isFromSource(8194) && motionEvent.isFromSource(16386) && (toolType == 2 || toolType == 4)) {
            getPointerIconService().getClass();
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        m40 m40Var;
        if (this.g) {
            int[] iArr = et.a;
            m40 m40Var2 = m40.e;
            if (i != 0) {
                if (i != 1) {
                    m40Var = null;
                } else {
                    m40Var = m40.f;
                }
            } else {
                m40Var = m40Var2;
            }
            if (m40Var != null) {
                m40Var2 = m40Var;
            }
            setLayoutDirection(m40Var2);
        }
    }

    /* JADX WARN: Type inference failed for: r5v0, types: [ft0, k2] */
    @Override // android.view.View
    public final void onScrollCaptureSearch(Rect rect, Point point, Consumer consumer) {
        t70 t70Var;
        Object obj;
        if (Build.VERSION.SDK_INT >= 31 && (t70Var = this.M0) != null) {
            vu0 semanticsOwner = getSemanticsOwner();
            yj coroutineContext = getCoroutineContext();
            ef0 ef0Var = new ef0(new gt0[16]);
            y20.B(semanticsOwner.a(), 0, new k2(1, 8, ef0.class, ef0Var, "add", "add(Ljava/lang/Object;)Z"));
            Arrays.sort(ef0Var.e, 0, ef0Var.g, new mf(0, new gv[]{oj0.k, oj0.l}));
            int i = ef0Var.g;
            if (i == 0) {
                obj = null;
            } else {
                obj = ef0Var.e[i - 1];
            }
            gt0 gt0Var = (gt0) obj;
            if (gt0Var != null) {
                z10 z10Var = gt0Var.c;
                dh dhVar = new dh(gt0Var.a, z10Var, dl.d(coroutineContext), t70Var, this);
                ng0 ng0Var = gt0Var.d;
                long j = (z10Var.a << 32) | (z10Var.b & 4294967295L);
                ScrollCaptureTarget l = l4.l(this, m20.I(k81.H(o30.n(ng0Var).U(ng0Var, true))), new Point((int) (j >> 32), (int) (j & 4294967295L)), dhVar);
                l4.y(l, m20.I(z10Var));
                consumer.accept(l);
            }
        }
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public final void onScrollChanged() {
        M();
    }

    @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
    public final void onTouchModeChanged(boolean z) {
        int i;
        if (z) {
            i = 1;
        } else {
            i = 2;
        }
        this.v0.a.setValue(new d10(i));
    }

    @Override // android.view.View
    public final void onVirtualViewTranslationResponses(LongSparseArray longSparseArray) {
        t4 t4Var = this.E;
        t4Var.getClass();
        if (Build.VERSION.SDK_INT < 31) {
            return;
        }
        if (o20.e(Looper.getMainLooper().getThread(), Thread.currentThread())) {
            o20.m(t4Var, longSparseArray);
        } else {
            t4Var.e.post(new r4(0, t4Var, longSparseArray));
        }
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        boolean v;
        this.K0 = true;
        super.onWindowFocusChanged(z);
        if (z && Build.VERSION.SDK_INT < 30 && getShowLayoutBounds() != (v = k81.v())) {
            setShowLayoutBounds(v);
            q(getRoot());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00cc A[Catch: all -> 0x002b, TryCatch #2 {all -> 0x002b, blocks: (B:5:0x0018, B:7:0x0021, B:25:0x00c4, B:27:0x00cc, B:28:0x00cf, B:30:0x00d3, B:32:0x00d9, B:34:0x00dd, B:35:0x00e3, B:38:0x00eb, B:41:0x00f3, B:42:0x00ff, B:44:0x0105, B:46:0x010b, B:48:0x0111, B:49:0x0117, B:51:0x011b, B:52:0x011f, B:57:0x0132, B:59:0x0136, B:60:0x013d, B:66:0x014e, B:67:0x0158, B:69:0x0160, B:70:0x0163, B:76:0x016a), top: B:4:0x0018 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00dd A[Catch: all -> 0x002b, TryCatch #2 {all -> 0x002b, blocks: (B:5:0x0018, B:7:0x0021, B:25:0x00c4, B:27:0x00cc, B:28:0x00cf, B:30:0x00d3, B:32:0x00d9, B:34:0x00dd, B:35:0x00e3, B:38:0x00eb, B:41:0x00f3, B:42:0x00ff, B:44:0x0105, B:46:0x010b, B:48:0x0111, B:49:0x0117, B:51:0x011b, B:52:0x011f, B:57:0x0132, B:59:0x0136, B:60:0x013d, B:66:0x014e, B:67:0x0158, B:69:0x0160, B:70:0x0163, B:76:0x016a), top: B:4:0x0018 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0111 A[Catch: all -> 0x002b, TryCatch #2 {all -> 0x002b, blocks: (B:5:0x0018, B:7:0x0021, B:25:0x00c4, B:27:0x00cc, B:28:0x00cf, B:30:0x00d3, B:32:0x00d9, B:34:0x00dd, B:35:0x00e3, B:38:0x00eb, B:41:0x00f3, B:42:0x00ff, B:44:0x0105, B:46:0x010b, B:48:0x0111, B:49:0x0117, B:51:0x011b, B:52:0x011f, B:57:0x0132, B:59:0x0136, B:60:0x013d, B:66:0x014e, B:67:0x0158, B:69:0x0160, B:70:0x0163, B:76:0x016a), top: B:4:0x0018 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x011b A[Catch: all -> 0x002b, TryCatch #2 {all -> 0x002b, blocks: (B:5:0x0018, B:7:0x0021, B:25:0x00c4, B:27:0x00cc, B:28:0x00cf, B:30:0x00d3, B:32:0x00d9, B:34:0x00dd, B:35:0x00e3, B:38:0x00eb, B:41:0x00f3, B:42:0x00ff, B:44:0x0105, B:46:0x010b, B:48:0x0111, B:49:0x0117, B:51:0x011b, B:52:0x011f, B:57:0x0132, B:59:0x0136, B:60:0x013d, B:66:0x014e, B:67:0x0158, B:69:0x0160, B:70:0x0163, B:76:0x016a), top: B:4:0x0018 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0136 A[Catch: all -> 0x002b, TryCatch #2 {all -> 0x002b, blocks: (B:5:0x0018, B:7:0x0021, B:25:0x00c4, B:27:0x00cc, B:28:0x00cf, B:30:0x00d3, B:32:0x00d9, B:34:0x00dd, B:35:0x00e3, B:38:0x00eb, B:41:0x00f3, B:42:0x00ff, B:44:0x0105, B:46:0x010b, B:48:0x0111, B:49:0x0117, B:51:0x011b, B:52:0x011f, B:57:0x0132, B:59:0x0136, B:60:0x013d, B:66:0x014e, B:67:0x0158, B:69:0x0160, B:70:0x0163, B:76:0x016a), top: B:4:0x0018 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x014e A[Catch: all -> 0x002b, TryCatch #2 {all -> 0x002b, blocks: (B:5:0x0018, B:7:0x0021, B:25:0x00c4, B:27:0x00cc, B:28:0x00cf, B:30:0x00d3, B:32:0x00d9, B:34:0x00dd, B:35:0x00e3, B:38:0x00eb, B:41:0x00f3, B:42:0x00ff, B:44:0x0105, B:46:0x010b, B:48:0x0111, B:49:0x0117, B:51:0x011b, B:52:0x011f, B:57:0x0132, B:59:0x0136, B:60:0x013d, B:66:0x014e, B:67:0x0158, B:69:0x0160, B:70:0x0163, B:76:0x016a), top: B:4:0x0018 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0160 A[Catch: all -> 0x002b, TryCatch #2 {all -> 0x002b, blocks: (B:5:0x0018, B:7:0x0021, B:25:0x00c4, B:27:0x00cc, B:28:0x00cf, B:30:0x00d3, B:32:0x00d9, B:34:0x00dd, B:35:0x00e3, B:38:0x00eb, B:41:0x00f3, B:42:0x00ff, B:44:0x0105, B:46:0x010b, B:48:0x0111, B:49:0x0117, B:51:0x011b, B:52:0x011f, B:57:0x0132, B:59:0x0136, B:60:0x013d, B:66:0x014e, B:67:0x0158, B:69:0x0160, B:70:0x0163, B:76:0x016a), top: B:4:0x0018 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0163 A[Catch: all -> 0x002b, TryCatch #2 {all -> 0x002b, blocks: (B:5:0x0018, B:7:0x0021, B:25:0x00c4, B:27:0x00cc, B:28:0x00cf, B:30:0x00d3, B:32:0x00d9, B:34:0x00dd, B:35:0x00e3, B:38:0x00eb, B:41:0x00f3, B:42:0x00ff, B:44:0x0105, B:46:0x010b, B:48:0x0111, B:49:0x0117, B:51:0x011b, B:52:0x011f, B:57:0x0132, B:59:0x0136, B:60:0x013d, B:66:0x014e, B:67:0x0158, B:69:0x0160, B:70:0x0163, B:76:0x016a), top: B:4:0x0018 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x004e A[Catch: all -> 0x0076, TryCatch #0 {all -> 0x0076, blocks: (B:90:0x0034, B:92:0x003e, B:97:0x004e, B:100:0x007d, B:102:0x0081, B:104:0x0090, B:106:0x0096, B:13:0x00a1, B:21:0x00b4, B:23:0x00ba, B:107:0x0056, B:113:0x0062, B:116:0x006a), top: B:89:0x0034 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int p(android.view.MotionEvent r17) {
        /*
            Method dump skipped, instructions count: 387
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.b4.p(android.view.MotionEvent):int");
    }

    public final void r(z40 z40Var) {
        this.c0.r(z40Var, false);
        ef0 w = z40Var.w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            r((z40) objArr[i2]);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean requestFocus(int i, Rect rect) {
        int i2;
        wo0 wo0Var;
        int i3 = 1;
        if (!isFocused()) {
            bt b = et.b(i);
            if (b != null) {
                i2 = b.a;
            } else {
                i2 = 7;
            }
            ht focusOwner = getFocusOwner();
            if (rect != null) {
                wo0Var = new wo0(rect.left, rect.top, rect.right, rect.bottom);
            } else {
                wo0Var = null;
            }
            Boolean e = ((lt) focusOwner).e(i2, wo0Var, new y3(i2, 0));
            Boolean bool = Boolean.TRUE;
            if (!o20.e(e, bool)) {
                if (!o20.e(((lt) getFocusOwner()).e(i2, null, new y3(i2, i3)), bool)) {
                    if (!hasFocus() || (i2 != 1 && i2 != 2)) {
                        return false;
                    }
                    return ((lt) getFocusOwner()).g(i2);
                }
            }
        }
        return true;
    }

    public void setAccessibilityEventBatchIntervalMillis(long j) {
        this.D.l = j;
    }

    public final void setComposeViewContext(nh nhVar) {
        gv gvVar;
        if (getCoroutineContext() != nhVar.b.j() && !((bf0) getRoot().m()).isEmpty()) {
            q00.a("Changing ComposeViewContext cannot change the coroutine context without disposing of the composition first.");
        }
        ww0 t = t20.t();
        if (t != null) {
            gvVar = t.e();
        } else {
            gvVar = null;
        }
        ww0 C = t20.C(t);
        try {
            nh nhVar2 = get_composeViewContext();
            if (nhVar != nhVar2) {
                if (isAttachedToWindow()) {
                    nhVar2.b();
                    nhVar.c();
                }
                set_composeViewContext(nhVar);
                setCoroutineContext(nhVar.b.j());
            }
        } finally {
            t20.K(t, C, gvVar);
        }
    }

    public final void setComposeViewContextIncrementedDuringInit$ui(boolean z) {
        this.L0 = z;
    }

    public final void setConfiguration(Configuration configuration) {
        this.N.setValue(configuration);
    }

    public final void setContentCaptureManager$ui(t4 t4Var) {
        this.E = t4Var;
    }

    public void setCoroutineContext(yj yjVar) {
        this.r = yjVar;
    }

    public final void setFrameEndScheduler$ui(m80 m80Var) {
        this.j = m80Var;
    }

    public final void setLastMatrixRecalculationAnimationTime$ui(long j) {
        this.h0 = j;
    }

    public final void setOnReadyForComposition(gv gvVar) {
        getDerivedIsAttached();
        if (!isAttachedToWindow() && !this.L0) {
            this.m0 = gvVar;
        } else {
            gvVar.e(getComposeViewContext());
        }
    }

    /* renamed from: setPrimaryDirectionalMotionAxisOverride-r2epLt8$ui, reason: not valid java name */
    public final void m2setPrimaryDirectionalMotionAxisOverrider2epLt8$ui(b00 b00Var) {
        this.h = b00Var;
    }

    public void setShowLayoutBounds(boolean z) {
        this.V = z;
    }

    public void setUncaughtExceptionHandler(dr0 dr0Var) {
        this.c0.getClass();
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    public final boolean u(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (0.0f <= x && x <= getWidth() && 0.0f <= y && y <= getHeight()) {
            return true;
        }
        return false;
    }

    public final boolean v(MotionEvent motionEvent) {
        MotionEvent motionEvent2;
        if (motionEvent.getPointerCount() != 1 || (motionEvent2 = this.y0) == null || motionEvent2.getPointerCount() != motionEvent.getPointerCount() || motionEvent.getRawX() != motionEvent2.getRawX() || motionEvent.getRawY() != motionEvent2.getRawY()) {
            return true;
        }
        return false;
    }

    public final long w(long j) {
        E();
        long y = m20.y(this.f0, j);
        float intBitsToFloat = Float.intBitsToFloat((int) (this.j0 >> 32)) + Float.intBitsToFloat((int) (y >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (this.j0 & 4294967295L)) + Float.intBitsToFloat((int) (y & 4294967295L));
        return (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L);
    }

    public final void x(boolean z) {
        t3 t3Var;
        mc0 mc0Var = this.c0;
        if (!mc0Var.b.x() && ((ef0) mc0Var.e.f).g == 0) {
            return;
        }
        Trace.beginSection("AndroidOwner:measureAndLayout");
        if (z) {
            try {
                t3Var = this.I0;
            } finally {
                Trace.endSection();
            }
        } else {
            t3Var = null;
        }
        if (mc0Var.l(t3Var)) {
            requestLayout();
        }
        mc0Var.b(false);
        getRectManager().a();
    }

    public final void y(z40 z40Var, long j) {
        mc0 mc0Var = this.c0;
        Trace.beginSection("AndroidOwner:measureAndLayout");
        try {
            mc0Var.m(z40Var, j);
            if (!mc0Var.b.x()) {
                mc0Var.b(false);
                getRectManager().a();
            }
        } finally {
            Trace.endSection();
        }
    }

    public final void z() {
        pe0 pe0Var;
        g3 g3Var;
        Object[] objArr;
        if (this.R) {
            ox0 ox0Var = getSnapshotObserver().a;
            synchronized (ox0Var.g) {
                try {
                    ef0 ef0Var = ox0Var.f;
                    int i = ef0Var.g;
                    int i2 = 0;
                    int i3 = 0;
                    while (true) {
                        objArr = ef0Var.e;
                        if (i2 >= i) {
                            break;
                        }
                        nx0 nx0Var = (nx0) objArr[i2];
                        nx0Var.c();
                        if (!nx0Var.f.j()) {
                            i3++;
                        } else if (i3 > 0) {
                            Object[] objArr2 = ef0Var.e;
                            objArr2[i2 - i3] = objArr2[i2];
                        }
                        i2++;
                    }
                    int i4 = i - i3;
                    Arrays.fill(objArr, i4, i, (Object) null);
                    ef0Var.g = i4;
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.R = false;
        }
        t6 t6Var = this.W;
        if (t6Var != null) {
            l(t6Var);
        }
        if (k() && (g3Var = this.Q) != null) {
            ie0 ie0Var = g3Var.k;
            if (ie0Var.d == 0 && g3Var.l) {
                ((AutofillManager) g3Var.e.f).commit();
                g3Var.l = false;
            }
            if (ie0Var.d != 0) {
                g3Var.l = true;
            }
        }
        while (this.B0.i() && this.B0.f(0) != null) {
            int i5 = this.B0.b;
            int i6 = 0;
            while (true) {
                pe0Var = this.B0;
                if (i6 < i5) {
                    vu vuVar = (vu) pe0Var.f(i6);
                    this.B0.n(i6, null);
                    if (vuVar != null) {
                        vuVar.a();
                    }
                    i6++;
                }
            }
            pe0Var.l(0, i5);
        }
    }

    /* renamed from: getAccessibilityManager, reason: merged with bridge method [inline-methods] */
    public c3 m3getAccessibilityManager() {
        return this.F;
    }

    public k3 getClipboard() {
        return this.T;
    }

    public l3 getClipboardManager() {
        return this.S;
    }

    /* renamed from: getDragAndDropManager, reason: merged with bridge method [inline-methods] */
    public a5 m6getDragAndDropManager() {
        return this.s;
    }

    /* renamed from: getLayoutNodes, reason: merged with bridge method [inline-methods] */
    public he0 m7getLayoutNodes() {
        return this.A;
    }

    @Override // android.view.ViewGroup
    public final void addView(View view) {
        addView(view, -1);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, int i2) {
        ViewGroup.LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
        generateDefaultLayoutParams.width = i;
        generateDefaultLayoutParams.height = i2;
        addViewInLayout(view, -1, generateDefaultLayoutParams, true);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        addViewInLayout(view, i, layoutParams, true);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        addViewInLayout(view, -1, layoutParams, true);
    }

    @sm
    public static /* synthetic */ void getFontLoader$annotations() {
    }

    public static /* synthetic */ void getLastMatrixRecalculationAnimationTime$ui$annotations() {
    }

    /* renamed from: getPrimaryDirectionalMotionAxisOverride-dqNNBbU$ui$annotations, reason: not valid java name */
    public static /* synthetic */ void m0getPrimaryDirectionalMotionAxisOverridedqNNBbU$ui$annotations() {
    }

    public static /* synthetic */ void getRoot$annotations() {
    }

    public static /* synthetic */ void getShowLayoutBounds$annotations() {
    }

    @sm
    public static /* synthetic */ void getTextInputService$annotations() {
    }

    public static /* synthetic */ void getWindowInfo$annotations() {
    }

    public er0 getRootForTest() {
        return this;
    }

    public View getView() {
        return this;
    }

    @Override // defpackage.yl
    public final void a(j80 j80Var) {
    }

    @Override // defpackage.yl
    public final void c(j80 j80Var) {
    }

    @Override // defpackage.yl
    public final void d(j80 j80Var) {
    }

    @Override // defpackage.yl
    public final void f(j80 j80Var) {
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
    }

    public final void setUncaughtExceptionHandler$ui(dr0 dr0Var) {
    }
}

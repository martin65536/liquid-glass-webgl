package defpackage;

import android.graphics.Insets;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.view.WindowInsetsAnimation;
import android.view.autofill.AutofillId;
import android.view.contentcapture.ContentCaptureSession;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c4 implements gc, vq, os, kz0, rc, zi0, ss0 {
    public final /* synthetic */ int e;
    public Object f;
    public Object g;

    public c4(int i) {
        this.e = i;
        switch (i) {
            case 2:
                this.f = new qf0();
                this.g = new LinkedHashMap();
                bq0.a.add(this);
                return;
            case 5:
                this.f = new x41(0);
                this.g = new x41(0);
                return;
            case 12:
                this.f = new ve0();
                this.g = new ve0();
                return;
            case 13:
                this.f = new ef0(new z40[16]);
                return;
            case 18:
                this.f = new LinkedHashMap();
                this.g = new LinkedHashMap();
                return;
            case 24:
                this.f = new ey0(6);
                this.g = new vb0(16);
                return;
            case 25:
                this.f = new ef0(new Reference[16]);
                this.g = new ReferenceQueue();
                return;
            default:
                if (Build.VERSION.SDK_INT >= 26) {
                    this.f = new l1(this);
                    return;
                } else {
                    this.f = new l1(this);
                    return;
                }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [bd0] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4, types: [bd0] */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3, types: [ef0] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [ef0] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    public static void p(z40 z40Var) {
        if (z40Var.P > 0) {
            if (z40Var.I.d == v40.i && !z40Var.o() && !z40Var.p() && !z40Var.Q && z40Var.F()) {
                bd0 bd0Var = z40Var.H.f;
                if ((bd0Var.h & 256) != 0) {
                    while (bd0Var != null) {
                        if ((bd0Var.g & 256) != 0) {
                            jm jmVar = bd0Var;
                            ?? r5 = 0;
                            while (jmVar != 0) {
                                if (jmVar instanceof ww) {
                                    ww wwVar = (ww) jmVar;
                                    wwVar.E(k81.B(wwVar, 256));
                                } else if ((jmVar.g & 256) != 0 && (jmVar instanceof jm)) {
                                    bd0 bd0Var2 = jmVar.t;
                                    int i = 0;
                                    jmVar = jmVar;
                                    r5 = r5;
                                    while (bd0Var2 != null) {
                                        if ((bd0Var2.g & 256) != 0) {
                                            i++;
                                            r5 = r5;
                                            if (i == 1) {
                                                jmVar = bd0Var2;
                                            } else {
                                                if (r5 == 0) {
                                                    r5 = new ef0(new bd0[16]);
                                                }
                                                if (jmVar != 0) {
                                                    r5.b(jmVar);
                                                    jmVar = 0;
                                                }
                                                r5.b(bd0Var2);
                                            }
                                        }
                                        bd0Var2 = bd0Var2.j;
                                        jmVar = jmVar;
                                        r5 = r5;
                                    }
                                    if (i == 1) {
                                    }
                                }
                                jmVar = k81.h(r5);
                            }
                        }
                        if ((bd0Var.h & 256) == 0) {
                            break;
                        } else {
                            bd0Var = bd0Var.j;
                        }
                    }
                }
            }
            z40Var.O = false;
            ef0 w = z40Var.w();
            Object[] objArr = w.e;
            int i2 = w.g;
            for (int i3 = 0; i3 < i2; i3++) {
                p((z40) objArr[i3]);
            }
        }
    }

    @Override // defpackage.vq
    public Object a() {
        return (a41) this.f;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [ap0, java.lang.Object] */
    @Override // defpackage.os
    public Object b(ps psVar, ij ijVar) {
        Object b = ((ld) this.f).b(new ts(new Object(), psVar, (qo0) this.g), ijVar);
        if (b == ik.e) {
            return b;
        }
        return x31.a;
    }

    @Override // defpackage.rc
    public void cancel() {
        if (!((o8) this.g).compareAndSet(1, 1)) {
            ((y8) this.f).a();
        }
    }

    @Override // defpackage.kz0
    public void d(jz0 jz0Var) {
        int i;
        oe0 oe0Var = (oe0) this.g;
        oe0Var.a();
        qe0 qe0Var = (qe0) jz0Var.f;
        Object[] objArr = qe0Var.b;
        long[] jArr = qe0Var.c;
        int i2 = qe0Var.e;
        while (i2 != Integer.MAX_VALUE) {
            int i3 = (int) ((jArr[i2] >> 31) & 2147483647L);
            Object obj = objArr[i2];
            Object b = ((e60) this.f).b(obj);
            int d = oe0Var.d(b);
            if (d >= 0) {
                i = oe0Var.c[d];
            } else {
                i = 0;
            }
            if (i == 7) {
                jz0Var.remove(obj);
            } else {
                oe0Var.g(i + 1, b);
            }
            i2 = i3;
        }
    }

    @Override // defpackage.gc
    public void e(View view, float[] fArr) {
        m20.D(fArr);
        w(view, fArr);
    }

    @Override // defpackage.zi0
    public List f(Integer num) {
        List f = ((zi0) this.f).f(null);
        uw0 uw0Var = (uw0) this.g;
        int i = uw0Var.v;
        if (i < 0) {
            return f;
        }
        return me.b0(f31.o(uw0Var, num, i, Integer.valueOf(uw0Var.E(uw0Var.b, i))), f);
    }

    @Override // defpackage.ss0
    public Object g(Object obj) {
        return ((gv) this.g).e(obj);
    }

    @Override // defpackage.vq
    public boolean h(CharSequence charSequence, int i, int i2, n31 n31Var) {
        Spannable spannableString;
        if ((n31Var.c & 4) > 0) {
            return true;
        }
        if (((a41) this.f) == null) {
            if (charSequence instanceof Spannable) {
                spannableString = (Spannable) charSequence;
            } else {
                spannableString = new SpannableString(charSequence);
            }
            this.f = new a41(spannableString);
        }
        ((dt0) this.g).getClass();
        ((a41) this.f).setSpan(new o31(n31Var), i, i2, 33);
        return true;
    }

    @Override // defpackage.kz0
    public boolean i(Object obj, Object obj2) {
        e60 e60Var = (e60) this.f;
        return o20.e(e60Var.b(obj), e60Var.b(obj2));
    }

    @Override // defpackage.ss0
    public Object j(bs0 bs0Var, Object obj) {
        return ((kv) this.f).d(bs0Var, obj);
    }

    @Override // defpackage.zi0
    public boolean k() {
        return ((zi0) this.f).k();
    }

    public boolean l(long j) {
        Object obj;
        List list = (List) ((c4) this.g).f;
        int size = list.size();
        int i = 0;
        while (true) {
            if (i < size) {
                obj = list.get(i);
                if (n30.s(((wm0) obj).a, j)) {
                    break;
                }
                i++;
            } else {
                obj = null;
                break;
            }
        }
        wm0 wm0Var = (wm0) obj;
        if (wm0Var == null) {
            return false;
        }
        return wm0Var.h;
    }

    public Bundle m(String str) {
        Bundle bundle;
        os0 os0Var = (os0) this.f;
        if (os0Var.g) {
            Bundle bundle2 = os0Var.f;
            if (bundle2 == null) {
                return null;
            }
            if (bundle2.containsKey(str)) {
                bundle = o30.t(bundle2, str);
            } else {
                bundle = null;
            }
            bundle2.remove(str);
            if (bundle2.isEmpty()) {
                os0Var.f = null;
            }
            return bundle;
        }
        v7.o("You can 'consumeRestoredStateForKey' only after the corresponding component has moved to the 'CREATED' state");
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:408:0x089e, code lost:
    
        if (r1 == false) goto L435;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0137, code lost:
    
        if (defpackage.su0.j(4, r7).isEmpty() != false) goto L62;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:289:0x0603  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0616  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x0653  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0672  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x06a7  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x06bc  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x06c7  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x06cd  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x06e9  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x073a  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x075a  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x076c  */
    /* JADX WARN: Removed duplicated region for block: B:377:0x07f9  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x0818  */
    /* JADX WARN: Removed duplicated region for block: B:394:0x0865  */
    /* JADX WARN: Removed duplicated region for block: B:402:0x0884  */
    /* JADX WARN: Removed duplicated region for block: B:415:0x0880 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:417:0x081b  */
    /* JADX WARN: Removed duplicated region for block: B:420:0x08b0  */
    /* JADX WARN: Removed duplicated region for block: B:438:0x0921  */
    /* JADX WARN: Removed duplicated region for block: B:463:0x09bc  */
    /* JADX WARN: Removed duplicated region for block: B:466:0x09df A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:469:0x09ef  */
    /* JADX WARN: Removed duplicated region for block: B:471:0x09f3  */
    /* JADX WARN: Removed duplicated region for block: B:478:0x0a1d  */
    /* JADX WARN: Removed duplicated region for block: B:481:0x0a27  */
    /* JADX WARN: Removed duplicated region for block: B:498:0x0a6a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:501:0x0a7a  */
    /* JADX WARN: Removed duplicated region for block: B:503:0x0a7e  */
    /* JADX WARN: Removed duplicated region for block: B:510:0x0aa8  */
    /* JADX WARN: Removed duplicated region for block: B:513:0x0ab2  */
    /* JADX WARN: Removed duplicated region for block: B:521:0x0ad6  */
    /* JADX WARN: Removed duplicated region for block: B:524:0x0ae9  */
    /* JADX WARN: Removed duplicated region for block: B:527:0x0afc  */
    /* JADX WARN: Removed duplicated region for block: B:576:0x0c36  */
    /* JADX WARN: Removed duplicated region for block: B:579:0x0c47  */
    /* JADX WARN: Removed duplicated region for block: B:582:0x0c65  */
    /* JADX WARN: Removed duplicated region for block: B:585:0x0c7a  */
    /* JADX WARN: Removed duplicated region for block: B:587:0x0c5b  */
    /* JADX WARN: Removed duplicated region for block: B:588:0x0c3a  */
    /* JADX WARN: Removed duplicated region for block: B:589:0x0aed  */
    /* JADX WARN: Removed duplicated region for block: B:590:0x06d2  */
    /* JADX WARN: Removed duplicated region for block: B:591:0x06c9  */
    /* JADX WARN: Removed duplicated region for block: B:592:0x06be  */
    /* JADX WARN: Removed duplicated region for block: B:593:0x06af  */
    /* JADX WARN: Removed duplicated region for block: B:595:0x0696  */
    /* JADX WARN: Removed duplicated region for block: B:596:0x0658  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0c82  */
    /* JADX WARN: Type inference failed for: r2v24, types: [er] */
    /* JADX WARN: Type inference failed for: r2v25, types: [java.util.List, java.util.Collection] */
    /* JADX WARN: Type inference failed for: r2v26, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r49v1 */
    /* JADX WARN: Type inference failed for: r49v2, types: [java.lang.Throwable, k1] */
    /* JADX WARN: Type inference failed for: r49v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public defpackage.k1 n(int r50) {
        /*
            Method dump skipped, instructions count: 3244
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.c4.n(int):k1");
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0017, code lost:
    
        if (r3 < r1) goto L6;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void o() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.f
            ef0 r0 = (defpackage.ef0) r0
            qt r1 = defpackage.qt.d
            java.lang.Object[] r2 = r0.e
            int r3 = r0.g
            r4 = 0
            java.util.Arrays.sort(r2, r4, r3, r1)
            int r1 = r0.g
            java.lang.Object r2 = r6.g
            z40[] r2 = (defpackage.z40[]) r2
            if (r2 == 0) goto L19
            int r3 = r2.length
            if (r3 >= r1) goto L21
        L19:
            r2 = 16
            int r2 = java.lang.Math.max(r2, r1)
            z40[] r2 = new defpackage.z40[r2]
        L21:
            r3 = 0
            r6.g = r3
        L24:
            if (r4 >= r1) goto L2f
            java.lang.Object[] r5 = r0.e
            r5 = r5[r4]
            r2[r4] = r5
            int r4 = r4 + 1
            goto L24
        L2f:
            r0.g()
            int r1 = r1 + (-1)
        L34:
            r0 = -1
            if (r0 >= r1) goto L48
            r0 = r2[r1]
            r0.getClass()
            boolean r4 = r0.O
            if (r4 == 0) goto L43
            p(r0)
        L43:
            r2[r1] = r3
            int r1 = r1 + (-1)
            goto L34
        L48:
            r6.g = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.c4.o():void");
    }

    public ns0 q(String str) {
        ns0 ns0Var;
        os0 os0Var = (os0) this.f;
        synchronized (os0Var.c) {
            Iterator it = os0Var.d.entrySet().iterator();
            do {
                ns0Var = null;
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry entry = (Map.Entry) it.next();
                String str2 = (String) entry.getKey();
                ns0 ns0Var2 = (ns0) entry.getValue();
                if (o20.e(str2, str)) {
                    ns0Var = ns0Var2;
                }
            } while (ns0Var == null);
        }
        return ns0Var;
    }

    public AutofillId r(long j) {
        if (Build.VERSION.SDK_INT >= 29) {
            ContentCaptureSession d = h3.d(this.f);
            j1 t = m20.t((View) this.g);
            Objects.requireNonNull(t);
            return xi.b(d, z0.e(t.a), j);
        }
        return null;
    }

    public void s(Bundle bundle) {
        os0 os0Var = (os0) this.f;
        ps0 ps0Var = os0Var.a;
        if (!os0Var.e) {
            os0Var.a();
        }
        if (ps0Var.f().c.compareTo(a80.h) < 0) {
            if (!os0Var.g) {
                Bundle bundle2 = null;
                if (bundle != null && bundle.containsKey("androidx.lifecycle.BundlableSavedStateRegistry.key")) {
                    bundle2 = o30.t(bundle, "androidx.lifecycle.BundlableSavedStateRegistry.key");
                }
                os0Var.f = bundle2;
                os0Var.g = true;
                return;
            }
            v7.o("SavedStateRegistry was already restored.");
            return;
        }
        throw new IllegalStateException(("performRestore cannot be called when owner is " + ps0Var.f().c).toString());
    }

    public void t(Bundle bundle) {
        os0 os0Var = (os0) this.f;
        Bundle l = k81.l((xj0[]) Arrays.copyOf(new xj0[0], 0));
        Bundle bundle2 = os0Var.f;
        if (bundle2 != null) {
            l.putAll(bundle2);
        }
        synchronized (os0Var.c) {
            for (Map.Entry entry : os0Var.d.entrySet()) {
                String str = (String) entry.getKey();
                Bundle a = ((ns0) entry.getValue()).a();
                str.getClass();
                l.putBundle(str, a);
            }
        }
        if (!l.isEmpty()) {
            bundle.putBundle("androidx.lifecycle.BundlableSavedStateRegistry.key", l);
        }
    }

    public String toString() {
        switch (this.e) {
            case 1:
                return "AnimationResult(endReason=" + ((a7) this.g) + ", endState=" + ((d7) this.f) + ')';
            case 26:
                return "Bounds{lower=" + ((g10) this.f) + " upper=" + ((g10) this.g) + "}";
            default:
                return super.toString();
        }
    }

    public void u(String str, ns0 ns0Var) {
        ns0Var.getClass();
        os0 os0Var = (os0) this.f;
        synchronized (os0Var.c) {
            if (!os0Var.d.containsKey(str)) {
                os0Var.d.put(str, ns0Var);
            } else {
                throw new IllegalArgumentException("SavedStateProvider with the given key is already registered");
            }
        }
    }

    public void v() {
        if (((os0) this.f).h) {
            uo0 uo0Var = (uo0) this.g;
            if (uo0Var == null) {
                uo0Var = new uo0(this);
            }
            this.g = uo0Var;
            try {
                u70.class.getDeclaredConstructor(null);
                uo0 uo0Var2 = (uo0) this.g;
                if (uo0Var2 != null) {
                    uo0Var2.a.add(u70.class.getName());
                    return;
                }
                return;
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("Class " + u70.class.getSimpleName() + " must have default constructor in order to be automatically recreated", e);
            }
        }
        v7.o("Can not perform this action after onSaveInstanceState");
    }

    public void w(View view, float[] fArr) {
        float[] fArr2 = (float[]) this.f;
        Object parent = view.getParent();
        if (parent instanceof View) {
            w((View) parent, fArr);
            m20.D(fArr2);
            m20.K(fArr2, -view.getScrollX(), -view.getScrollY());
            o4.T(fArr, fArr2);
            float left = view.getLeft();
            float top = view.getTop();
            m20.D(fArr2);
            m20.K(fArr2, left, top);
            o4.T(fArr, fArr2);
        } else {
            int[] iArr = (int[]) this.g;
            view.getLocationInWindow(iArr);
            m20.D(fArr2);
            m20.K(fArr2, -view.getScrollX(), -view.getScrollY());
            o4.T(fArr, fArr2);
            float f = iArr[0];
            float f2 = iArr[1];
            m20.D(fArr2);
            m20.K(fArr2, f, f2);
            o4.T(fArr, fArr2);
        }
        Matrix matrix = view.getMatrix();
        if (!matrix.isIdentity()) {
            k81.I(fArr2, matrix);
            o4.T(fArr, fArr2);
        }
    }

    public /* synthetic */ c4(int i, boolean z) {
        this.e = i;
    }

    public c4(os0 os0Var, int i) {
        this.e = i;
        switch (i) {
            case 20:
                this.f = os0Var;
                this.g = new c4(os0Var, 19);
                return;
            default:
                this.f = os0Var;
                return;
        }
    }

    public c4(y8 y8Var) {
        this.e = 14;
        this.f = y8Var;
        this.g = new AtomicInteger(0);
    }

    public /* synthetic */ c4(int i, Object obj, Object obj2) {
        this.e = i;
        this.f = obj;
        this.g = obj2;
    }

    public c4(e60 e60Var) {
        this.e = 9;
        this.f = e60Var;
        oe0 oe0Var = xg0.a;
        this.g = new oe0();
    }

    public c4(WindowInsetsAnimation.Bounds bounds) {
        Insets lowerBound;
        Insets upperBound;
        this.e = 26;
        lowerBound = bounds.getLowerBound();
        this.f = g10.c(lowerBound);
        upperBound = bounds.getUpperBound();
        this.g = g10.c(upperBound);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public c4(h4 h4Var) {
        this(0);
        this.e = 0;
        this.g = h4Var;
    }

    public c4(float[] fArr) {
        this.e = 3;
        this.f = fArr;
        this.g = new int[2];
    }
}

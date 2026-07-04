package defpackage;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Trace;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.kyant.backdrop.catalog.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class h4 extends q0 implements View.OnAttachStateChangeListener, AccessibilityManager.AccessibilityStateChangeListener, AccessibilityManager.TouchExplorationStateChangeListener {
    public static final ge0 R;
    public final zb A;
    public boolean B;
    public d4 C;
    public he0 D;
    public final ie0 E;
    public final fe0 F;
    public final fe0 G;
    public final String H;
    public final String I;
    public final r7 J;
    public final he0 K;
    public tu0 L;
    public boolean M;
    public final fe0 N;
    public final n O;
    public final ArrayList P;
    public final g4 Q;
    public final b4 h;
    public int i = Integer.MIN_VALUE;
    public final g4 j = new g4(this, 0);
    public final AccessibilityManager k;
    public long l;
    public List m;
    public final c4 n;
    public int o;
    public int p;
    public k1 q;
    public k1 r;
    public boolean s;
    public final he0 t;
    public final he0 u;
    public final xx0 v;
    public final xx0 w;
    public int x;
    public Integer y;
    public final h8 z;

    static {
        int[] iArr = {R.id.accessibility_custom_action_0, R.id.accessibility_custom_action_1, R.id.accessibility_custom_action_2, R.id.accessibility_custom_action_3, R.id.accessibility_custom_action_4, R.id.accessibility_custom_action_5, R.id.accessibility_custom_action_6, R.id.accessibility_custom_action_7, R.id.accessibility_custom_action_8, R.id.accessibility_custom_action_9, R.id.accessibility_custom_action_10, R.id.accessibility_custom_action_11, R.id.accessibility_custom_action_12, R.id.accessibility_custom_action_13, R.id.accessibility_custom_action_14, R.id.accessibility_custom_action_15, R.id.accessibility_custom_action_16, R.id.accessibility_custom_action_17, R.id.accessibility_custom_action_18, R.id.accessibility_custom_action_19, R.id.accessibility_custom_action_20, R.id.accessibility_custom_action_21, R.id.accessibility_custom_action_22, R.id.accessibility_custom_action_23, R.id.accessibility_custom_action_24, R.id.accessibility_custom_action_25, R.id.accessibility_custom_action_26, R.id.accessibility_custom_action_27, R.id.accessibility_custom_action_28, R.id.accessibility_custom_action_29, R.id.accessibility_custom_action_30, R.id.accessibility_custom_action_31};
        ge0 ge0Var = s10.a;
        ge0 ge0Var2 = new ge0(32);
        int i = ge0Var2.b;
        if (i >= 0) {
            int i2 = i + 32;
            int[] iArr2 = ge0Var2.a;
            if (iArr2.length < i2) {
                ge0Var2.a = Arrays.copyOf(iArr2, Math.max(i2, (iArr2.length * 3) / 2));
            }
            int[] iArr3 = ge0Var2.a;
            int i3 = ge0Var2.b;
            if (i != i3) {
                i8.L(iArr3, iArr3, i2, i, i3);
            }
            i8.O(iArr, iArr3, i, 0, 12);
            ge0Var2.b += 32;
            R = ge0Var2;
            return;
        }
        v7.f("");
    }

    public h4(b4 b4Var) {
        this.h = b4Var;
        Object systemService = b4Var.getContext().getSystemService("accessibility");
        systemService.getClass();
        this.k = (AccessibilityManager) systemService;
        this.l = 100L;
        new Handler(Looper.getMainLooper());
        this.n = new c4(this);
        this.o = Integer.MIN_VALUE;
        this.p = Integer.MIN_VALUE;
        this.t = new he0();
        this.u = new he0();
        this.v = new xx0();
        this.w = new xx0();
        this.x = -1;
        this.z = new h8();
        this.A = f31.c(1, 6, null);
        this.B = true;
        he0 he0Var = u10.a;
        he0Var.getClass();
        this.D = he0Var;
        this.E = new ie0();
        this.F = new fe0();
        this.G = new fe0();
        this.H = "android.view.accessibility.extra.EXTRA_DATA_TEST_TRAVERSALBEFORE_VAL";
        this.I = "android.view.accessibility.extra.EXTRA_DATA_TEST_TRAVERSALAFTER_VAL";
        this.J = new r7(13);
        this.K = new he0();
        this.L = new tu0(b4Var.getSemanticsOwner().a(), he0Var);
        int i = r10.a;
        this.N = new fe0();
        b4Var.addOnAttachStateChangeListener(this);
        this.O = new n(1, this);
        this.P = new ArrayList();
        this.Q = new g4(this, 1);
    }

    public static Rect D(g30 g30Var, float f, float f2) {
        if (!(g30Var instanceof gj0) && !(g30Var instanceof hj0)) {
            return null;
        }
        wo0 r = g30Var.r();
        return new Rect((int) (r.a + f), (int) (r.b + f2), (int) (r.c + f), (int) (r.d + f2));
    }

    public static float[] F(g30 g30Var) {
        if (g30Var instanceof hj0) {
            gr0 gr0Var = ((hj0) g30Var).a;
            long j = gr0Var.h;
            long j2 = gr0Var.g;
            long j3 = gr0Var.f;
            long j4 = gr0Var.e;
            return new float[]{Float.intBitsToFloat((int) (j4 >> 32)), Float.intBitsToFloat((int) (j4 & 4294967295L)), Float.intBitsToFloat((int) (j3 >> 32)), Float.intBitsToFloat((int) (j3 & 4294967295L)), Float.intBitsToFloat((int) (j2 >> 32)), Float.intBitsToFloat((int) (j2 & 4294967295L)), Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L))};
        }
        return null;
    }

    public static Region G(g30 g30Var, float f, float f2) {
        if (g30Var instanceof fj0) {
            fj0 fj0Var = (fj0) g30Var;
            wo0 d = fj0Var.r().d(f, f2);
            Region region = new Region(new Rect((int) (d.a + 0.0f), (int) (d.b + 0.0f), (int) (d.c + 0.0f), (int) (d.d + 0.0f)));
            Region region2 = new Region();
            y5 y5Var = fj0Var.a;
            if (y5Var instanceof y5) {
                Path path = y5Var.a;
                path.offset(f, f2);
                region2.setPath(path, region);
                return region2;
            }
            throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
        }
        return null;
    }

    public static CharSequence H(CharSequence charSequence) {
        if (charSequence.length() != 0) {
            int i = 100000;
            if (charSequence.length() > 100000) {
                if (Character.isHighSurrogate(charSequence.charAt(99999)) && Character.isLowSurrogate(charSequence.charAt(100000))) {
                    i = 99999;
                }
                CharSequence subSequence = charSequence.subSequence(0, i);
                subSequence.getClass();
                return subSequence;
            }
        }
        return charSequence;
    }

    public static String l(su0 su0Var) {
        l7 l7Var;
        if (su0Var != null) {
            nu0 nu0Var = su0Var.d;
            ve0 ve0Var = nu0Var.e;
            av0 av0Var = wu0.a;
            if (ve0Var.c(av0Var)) {
                return ma0.a((List) nu0Var.d(av0Var), ",", null, 62);
            }
            av0 av0Var2 = wu0.D;
            if (ve0Var.c(av0Var2)) {
                Object g = ve0Var.g(av0Var2);
                if (g == null) {
                    g = null;
                }
                l7 l7Var2 = (l7) g;
                if (l7Var2 != null) {
                    return l7Var2.f;
                }
            } else {
                Object g2 = ve0Var.g(wu0.z);
                if (g2 == null) {
                    g2 = null;
                }
                List list = (List) g2;
                if (list != null && (l7Var = (l7) me.T(list)) != null) {
                    return l7Var.f;
                }
            }
        }
        return null;
    }

    public static final boolean p(et0 et0Var, float f) {
        vu vuVar = et0Var.a;
        if (f >= 0.0f || ((Number) vuVar.a()).floatValue() <= 0.0f) {
            if (f > 0.0f && ((Number) vuVar.a()).floatValue() < ((Number) et0Var.b.a()).floatValue()) {
                return true;
            }
            return false;
        }
        return true;
    }

    public static final boolean q(et0 et0Var) {
        vu vuVar = et0Var.a;
        if (((Number) vuVar.a()).floatValue() > 0.0f) {
            return true;
        }
        ((Number) vuVar.a()).floatValue();
        ((Number) et0Var.b.a()).floatValue();
        return false;
    }

    public static final boolean r(et0 et0Var) {
        vu vuVar = et0Var.a;
        if (((Number) vuVar.a()).floatValue() < ((Number) et0Var.b.a()).floatValue()) {
            return true;
        }
        ((Number) vuVar.a()).floatValue();
        return false;
    }

    public static /* synthetic */ void w(h4 h4Var, int i, int i2, Integer num, int i3) {
        if ((i3 & 4) != 0) {
            num = null;
        }
        h4Var.v(i, i2, num, null);
    }

    public final void A(z40 z40Var, ie0 ie0Var) {
        nu0 u;
        if (z40Var.E() && !this.h.getAndroidViewsHandler$ui().getLayoutNodeToHolder().containsKey(z40Var)) {
            z40 z40Var2 = null;
            if (!z40Var.H.d(8)) {
                z40Var = z40Var.s();
                while (true) {
                    if (z40Var != null) {
                        if (z40Var.H.d(8)) {
                            break;
                        } else {
                            z40Var = z40Var.s();
                        }
                    } else {
                        z40Var = null;
                        break;
                    }
                }
            }
            if (z40Var != null && (u = z40Var.u()) != null) {
                if (!u.g) {
                    z40 s = z40Var.s();
                    while (true) {
                        if (s != null) {
                            nu0 u2 = s.u();
                            if (u2 != null && u2.g) {
                                z40Var2 = s;
                                break;
                            }
                            s = s.s();
                        } else {
                            break;
                        }
                    }
                    if (z40Var2 != null) {
                        z40Var = z40Var2;
                    }
                }
                int i = z40Var.f;
                if (ie0Var.a(i)) {
                    w(this, s(i), 2048, 1, 8);
                }
            }
        }
    }

    public final void B(z40 z40Var) {
        if (z40Var.E() && !this.h.getAndroidViewsHandler$ui().getLayoutNodeToHolder().containsKey(z40Var)) {
            int i = z40Var.f;
            et0 et0Var = (et0) this.t.b(i);
            et0 et0Var2 = (et0) this.u.b(i);
            if (et0Var == null && et0Var2 == null) {
                return;
            }
            AccessibilityEvent g = g(i, 4096);
            if (et0Var != null) {
                g.setScrollX((int) ((Number) et0Var.a.a()).floatValue());
                g.setMaxScrollX((int) ((Number) et0Var.b.a()).floatValue());
            }
            if (et0Var2 != null) {
                g.setScrollY((int) ((Number) et0Var2.a.a()).floatValue());
                g.setMaxScrollY((int) ((Number) et0Var2.b.a()).floatValue());
            }
            u(g);
        }
    }

    public final boolean C(su0 su0Var, int i, int i2, boolean z) {
        String l;
        Integer num;
        Integer num2;
        nu0 nu0Var = su0Var.d;
        int i3 = su0Var.f;
        av0 av0Var = mu0.j;
        boolean z2 = false;
        if (nu0Var.e.c(av0Var) && n20.f(su0Var)) {
            lv lvVar = (lv) ((n0) su0Var.d.d(av0Var)).b;
            if (lvVar != null) {
                return ((Boolean) lvVar.c(Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z))).booleanValue();
            }
        } else if ((i != i2 || i2 != this.x) && (l = l(su0Var)) != null) {
            if (i < 0 || i != i2 || i2 > l.length()) {
                i = -1;
            }
            this.x = i;
            if (l.length() > 0) {
                z2 = true;
            }
            int s = s(i3);
            Integer num3 = null;
            if (z2) {
                num = Integer.valueOf(this.x);
            } else {
                num = null;
            }
            if (z2) {
                num2 = Integer.valueOf(this.x);
            } else {
                num2 = null;
            }
            if (z2) {
                num3 = Integer.valueOf(l.length());
            }
            u(h(s, num, num2, num3, l));
            y(i3);
            return true;
        }
        return false;
    }

    public final Rect E(float f, float f2, float f3, float f4) {
        long floatToRawIntBits = Float.floatToRawIntBits(f);
        b4 b4Var = this.h;
        long w = b4Var.w((Float.floatToRawIntBits(f2) & 4294967295L) | (floatToRawIntBits << 32));
        long w2 = b4Var.w((Float.floatToRawIntBits(f4) & 4294967295L) | (Float.floatToRawIntBits(f3) << 32));
        int i = (int) (w >> 32);
        int i2 = (int) (w2 >> 32);
        int i3 = (int) (w & 4294967295L);
        int i4 = (int) (w2 & 4294967295L);
        return new Rect((int) Math.floor(Math.min(Float.intBitsToFloat(i), Float.intBitsToFloat(i2))), (int) Math.floor(Math.min(Float.intBitsToFloat(i3), Float.intBitsToFloat(i4))), (int) Math.ceil(Math.max(Float.intBitsToFloat(i), Float.intBitsToFloat(i2))), (int) Math.ceil(Math.max(Float.intBitsToFloat(i3), Float.intBitsToFloat(i4))));
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x013b, code lost:
    
        r28 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0145, code lost:
    
        if (((r7 & ((~r7) << 6)) & r20) == 0) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0147, code lost:
    
        r25 = -1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void I() {
        /*
            Method dump skipped, instructions count: 526
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.h4.I():void");
    }

    @Override // defpackage.q0
    public final c4 a(View view) {
        return this.n;
    }

    public final void b(int i, k1 k1Var, String str, Bundle bundle) {
        su0 su0Var;
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        Object obj5;
        int i2;
        int i3;
        boolean z;
        float h;
        float h2;
        float g;
        float g2;
        int i4;
        b4 b4Var;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        wo0 wo0Var;
        RectF rectF;
        AccessibilityNodeInfo accessibilityNodeInfo = k1Var.a;
        uu0 uu0Var = (uu0) k().b(i);
        if (uu0Var != null && (su0Var = uu0Var.a) != null) {
            z40 z40Var = su0Var.c;
            nu0 nu0Var = su0Var.d;
            ve0 ve0Var = nu0Var.e;
            String l = l(su0Var);
            if (o20.e(str, this.H)) {
                int d = this.F.d(i);
                if (d != -1) {
                    accessibilityNodeInfo.getExtras().putInt(str, d);
                    return;
                }
                return;
            }
            if (o20.e(str, this.I)) {
                int d2 = this.G.d(i);
                if (d2 != -1) {
                    accessibilityNodeInfo.getExtras().putInt(str, d2);
                    return;
                }
                return;
            }
            boolean c = ve0Var.c(mu0.a);
            b4 b4Var2 = this.h;
            boolean z6 = false;
            if (c && bundle != null && o20.e(str, "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY")) {
                int i5 = bundle.getInt("android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX", -1);
                int i6 = bundle.getInt("android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH", -1);
                if (i6 > 0 && i5 >= 0) {
                    if (l != null) {
                        i2 = l.length();
                    } else {
                        i2 = Integer.MAX_VALUE;
                    }
                    if (i5 < i2) {
                        h11 l2 = y20.l(nu0Var);
                        if (l2 != null) {
                            ArrayList arrayList = new ArrayList();
                            int i7 = 0;
                            while (i7 < i6) {
                                int i8 = i5 + i7;
                                if (i8 >= l2.a.a.f.length()) {
                                    arrayList.add(z6);
                                    i3 = i5;
                                    i4 = i6;
                                    b4Var = b4Var2;
                                } else {
                                    xd0 xd0Var = l2.b;
                                    l7 l7Var = (l7) ((e3) xd0Var.c).a;
                                    if (i8 < 0 || i8 >= l7Var.f.length()) {
                                        r00.a("offset(" + i8 + ") is out of bounds [0, " + l7Var.f.length() + ')');
                                    }
                                    ArrayList arrayList2 = (ArrayList) xd0Var.e;
                                    yj0 yj0Var = (yj0) arrayList2.get(m20.r(i8, arrayList2));
                                    t5 t5Var = yj0Var.a;
                                    int a = yj0Var.a(i8);
                                    CharSequence charSequence = t5Var.e;
                                    if (a < 0 || a >= charSequence.length()) {
                                        r00.a("offset(" + a + ") is out of bounds [0," + charSequence.length() + ')');
                                    }
                                    f11 f11Var = t5Var.d;
                                    Layout layout = f11Var.e;
                                    int lineForOffset = layout.getLineForOffset(a);
                                    float f = f11Var.f(lineForOffset);
                                    float d3 = f11Var.d(lineForOffset);
                                    i3 = i5;
                                    if (layout.getParagraphDirection(lineForOffset) == 1) {
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                    boolean isRtlCharAt = layout.isRtlCharAt(a);
                                    if (z && !isRtlCharAt) {
                                        h = f11Var.g(a, false);
                                        h2 = f11Var.g(a + 1, true);
                                    } else {
                                        if (z && isRtlCharAt) {
                                            g = f11Var.h(a, false);
                                            g2 = f11Var.h(a + 1, true);
                                        } else if (isRtlCharAt) {
                                            g = f11Var.g(a, false);
                                            g2 = f11Var.g(a + 1, true);
                                        } else {
                                            h = f11Var.h(a, false);
                                            h2 = f11Var.h(a + 1, true);
                                        }
                                        float f2 = g;
                                        h = g2;
                                        h2 = f2;
                                    }
                                    RectF rectF2 = new RectF(h, f, h2, d3);
                                    float f3 = rectF2.left;
                                    float f4 = rectF2.top;
                                    float f5 = rectF2.right;
                                    float f6 = rectF2.bottom;
                                    i4 = i6;
                                    long floatToRawIntBits = (Float.floatToRawIntBits(yj0Var.f) & 4294967295L) | (Float.floatToRawIntBits(0.0f) << 32);
                                    b4Var = b4Var2;
                                    int i9 = (int) (floatToRawIntBits >> 32);
                                    int i10 = (int) (floatToRawIntBits & 4294967295L);
                                    wo0 wo0Var2 = new wo0(Float.intBitsToFloat(i9) + f3, Float.intBitsToFloat(i10) + f4, Float.intBitsToFloat(i9) + f5, Float.intBitsToFloat(i10) + f6);
                                    ng0 d4 = su0Var.d();
                                    long j = 0;
                                    if (d4 != null) {
                                        if (!d4.P0().r) {
                                            d4 = null;
                                        }
                                        if (d4 != null) {
                                            j = d4.Y0(0L);
                                        }
                                    }
                                    wo0 e = wo0Var2.e(j);
                                    wo0 g3 = su0Var.g();
                                    if (e.a < g3.c) {
                                        z2 = true;
                                    } else {
                                        z2 = false;
                                    }
                                    if (g3.a < e.c) {
                                        z3 = true;
                                    } else {
                                        z3 = false;
                                    }
                                    boolean z7 = z2 & z3;
                                    if (e.b < g3.d) {
                                        z4 = true;
                                    } else {
                                        z4 = false;
                                    }
                                    boolean z8 = z7 & z4;
                                    if (g3.b < e.d) {
                                        z5 = true;
                                    } else {
                                        z5 = false;
                                    }
                                    if (z8 & z5) {
                                        wo0Var = e.c(g3);
                                    } else {
                                        wo0Var = null;
                                    }
                                    if (wo0Var != null) {
                                        long w = b4Var.w((Float.floatToRawIntBits(wo0Var.a) << 32) | (Float.floatToRawIntBits(wo0Var.b) & 4294967295L));
                                        float f7 = wo0Var.c;
                                        long w2 = b4Var.w((Float.floatToRawIntBits(wo0Var.d) & 4294967295L) | (Float.floatToRawIntBits(f7) << 32));
                                        int i11 = (int) (w >> 32);
                                        int i12 = (int) (w2 >> 32);
                                        int i13 = (int) (w & 4294967295L);
                                        int i14 = (int) (w2 & 4294967295L);
                                        rectF = new RectF(Math.min(Float.intBitsToFloat(i11), Float.intBitsToFloat(i12)), Math.min(Float.intBitsToFloat(i13), Float.intBitsToFloat(i14)), Math.max(Float.intBitsToFloat(i11), Float.intBitsToFloat(i12)), Math.max(Float.intBitsToFloat(i13), Float.intBitsToFloat(i14)));
                                    } else {
                                        rectF = null;
                                    }
                                    arrayList.add(rectF);
                                }
                                i7++;
                                i5 = i3;
                                i6 = i4;
                                b4Var2 = b4Var;
                                z6 = false;
                            }
                            accessibilityNodeInfo.getExtras().putParcelableArray(str, (Parcelable[]) arrayList.toArray(new RectF[0]));
                            return;
                        }
                        return;
                    }
                }
                Log.e("AccessibilityDelegate", "Invalid arguments for accessibility character locations");
                return;
            }
            av0 av0Var = wu0.x;
            if (ve0Var.c(av0Var) && bundle != null && o20.e(str, "androidx.compose.ui.semantics.testTag")) {
                Object g4 = ve0Var.g(av0Var);
                if (g4 == null) {
                    obj5 = null;
                } else {
                    obj5 = g4;
                }
                String str2 = (String) obj5;
                if (str2 != null) {
                    accessibilityNodeInfo.getExtras().putCharSequence(str, str2);
                    return;
                }
                return;
            }
            if (o20.e(str, "androidx.compose.ui.semantics.id")) {
                accessibilityNodeInfo.getExtras().putInt(str, su0Var.f);
                return;
            }
            if (o20.e(str, "androidx.compose.ui.semantics.shapeType")) {
                Object g5 = ve0Var.g(wu0.M);
                if (g5 == null) {
                    obj4 = null;
                } else {
                    obj4 = g5;
                }
                zv0 zv0Var = (zv0) obj4;
                if (zv0Var != null) {
                    Rect rect = new Rect();
                    accessibilityNodeInfo.getBoundsInScreen(rect);
                    wo0 m = m(su0Var, rect, zv0Var);
                    float f8 = m.b;
                    float f9 = m.a;
                    g30 b = zv0Var.b(m.b(), z40Var.B, b4Var2.getDensity());
                    if (b instanceof gj0) {
                        accessibilityNodeInfo.getExtras().putInt("androidx.compose.ui.semantics.shapeType", 0);
                        accessibilityNodeInfo.getExtras().putParcelable("androidx.compose.ui.semantics.shapeRect", D(b, f9, f8));
                        return;
                    } else if (b instanceof hj0) {
                        accessibilityNodeInfo.getExtras().putInt("androidx.compose.ui.semantics.shapeType", 1);
                        accessibilityNodeInfo.getExtras().putParcelable("androidx.compose.ui.semantics.shapeRect", D(b, f9, f8));
                        accessibilityNodeInfo.getExtras().putFloatArray("androidx.compose.ui.semantics.shapeCorners", F(b));
                        return;
                    } else if (b instanceof fj0) {
                        accessibilityNodeInfo.getExtras().putInt("androidx.compose.ui.semantics.shapeType", 2);
                        accessibilityNodeInfo.getExtras().putParcelable("androidx.compose.ui.semantics.shapeRegion", G(b, f9, f8));
                        return;
                    } else {
                        v7.k();
                        return;
                    }
                }
                return;
            }
            if (o20.e(str, "androidx.compose.ui.semantics.shapeRect")) {
                Object g6 = ve0Var.g(wu0.M);
                if (g6 == null) {
                    obj3 = null;
                } else {
                    obj3 = g6;
                }
                zv0 zv0Var2 = (zv0) obj3;
                if (zv0Var2 != null) {
                    Rect rect2 = new Rect();
                    accessibilityNodeInfo.getBoundsInScreen(rect2);
                    wo0 m2 = m(su0Var, rect2, zv0Var2);
                    Rect D = D(zv0Var2.b(m2.b(), z40Var.B, b4Var2.getDensity()), m2.a, m2.b);
                    if (D != null) {
                        accessibilityNodeInfo.getExtras().putParcelable("androidx.compose.ui.semantics.shapeRect", D);
                        return;
                    }
                    return;
                }
                return;
            }
            if (o20.e(str, "androidx.compose.ui.semantics.shapeCorners")) {
                Object g7 = ve0Var.g(wu0.M);
                if (g7 == null) {
                    obj2 = null;
                } else {
                    obj2 = g7;
                }
                zv0 zv0Var3 = (zv0) obj2;
                if (zv0Var3 != null) {
                    Rect rect3 = new Rect();
                    accessibilityNodeInfo.getBoundsInScreen(rect3);
                    float[] F = F(zv0Var3.b(m(su0Var, rect3, zv0Var3).b(), z40Var.B, b4Var2.getDensity()));
                    if (F != null) {
                        accessibilityNodeInfo.getExtras().putFloatArray("androidx.compose.ui.semantics.shapeCorners", F);
                        return;
                    }
                    return;
                }
                return;
            }
            if (o20.e(str, "androidx.compose.ui.semantics.shapeRegion")) {
                Object g8 = ve0Var.g(wu0.M);
                if (g8 == null) {
                    obj = null;
                } else {
                    obj = g8;
                }
                zv0 zv0Var4 = (zv0) obj;
                if (zv0Var4 != null) {
                    Rect rect4 = new Rect();
                    accessibilityNodeInfo.getBoundsInScreen(rect4);
                    wo0 m3 = m(su0Var, rect4, zv0Var4);
                    Region G = G(zv0Var4.b(m3.b(), z40Var.B, b4Var2.getDensity()), m3.a, m3.b);
                    if (G != null) {
                        accessibilityNodeInfo.getExtras().putParcelable("androidx.compose.ui.semantics.shapeRegion", G);
                    }
                }
            }
        }
    }

    public final Rect c(uu0 uu0Var) {
        z10 z10Var = uu0Var.b;
        return E(z10Var.a, z10Var.b, z10Var.c, z10Var.d);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00f7, code lost:
    
        if (defpackage.f31.r(r4, r2) == r7) goto L111;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0076 A[Catch: all -> 0x0037, TryCatch #0 {all -> 0x0037, blocks: (B:12:0x0030, B:15:0x005c, B:21:0x006e, B:23:0x0076, B:25:0x007f, B:27:0x0085, B:29:0x0094, B:31:0x009c, B:54:0x0046, B:56:0x004d), top: B:7:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0028  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:39:0x00f7 -> B:14:0x00fa). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object d(defpackage.jj r17) {
        /*
            Method dump skipped, instructions count: 267
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.h4.d(jj):java.lang.Object");
    }

    public final boolean e(boolean z, int i, long j) {
        av0 av0Var;
        int i2;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        if (o20.e(Looper.getMainLooper().getThread(), Thread.currentThread())) {
            t10 k = k();
            if (!ch0.c(j, 9205357640488583168L) && (((9223372034707292159L & j) + 36028792732385279L) & (-9223372034707292160L)) == 0) {
                if (z) {
                    av0Var = wu0.v;
                } else if (!z) {
                    av0Var = wu0.u;
                } else {
                    v7.k();
                    return false;
                }
                Object[] objArr = k.c;
                long[] jArr = k.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i3 = 0;
                    boolean z6 = false;
                    while (true) {
                        long j2 = jArr[i3];
                        if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i4 = 8;
                            int i5 = 8 - ((~(i3 - length)) >>> 31);
                            int i6 = 0;
                            while (i6 < i5) {
                                if ((255 & j2) < 128) {
                                    uu0 uu0Var = (uu0) objArr[(i3 << 3) + i6];
                                    z10 z10Var = uu0Var.b;
                                    float f = z10Var.a;
                                    i2 = i4;
                                    float f2 = z10Var.b;
                                    float f3 = z10Var.c;
                                    float f4 = z10Var.d;
                                    float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32));
                                    float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L));
                                    if (intBitsToFloat >= f) {
                                        z2 = true;
                                    } else {
                                        z2 = false;
                                    }
                                    if (intBitsToFloat < f3) {
                                        z3 = true;
                                    } else {
                                        z3 = false;
                                    }
                                    boolean z7 = z2 & z3;
                                    if (intBitsToFloat2 >= f2) {
                                        z4 = true;
                                    } else {
                                        z4 = false;
                                    }
                                    boolean z8 = z7 & z4;
                                    if (intBitsToFloat2 < f4) {
                                        z5 = true;
                                    } else {
                                        z5 = false;
                                    }
                                    if (z5 & z8) {
                                        Object g = uu0Var.a.d.e.g(av0Var);
                                        if (g == null) {
                                            g = null;
                                        }
                                        et0 et0Var = (et0) g;
                                        if (et0Var != null) {
                                            vu vuVar = et0Var.a;
                                            if (i < 0) {
                                                if (((Number) vuVar.a()).floatValue() <= 0.0f) {
                                                }
                                                z6 = true;
                                            } else {
                                                if (((Number) vuVar.a()).floatValue() >= ((Number) et0Var.b.a()).floatValue()) {
                                                }
                                                z6 = true;
                                            }
                                        }
                                    }
                                } else {
                                    i2 = i4;
                                }
                                j2 >>= i2;
                                i6++;
                                i4 = i2;
                            }
                            if (i5 != i4) {
                                return z6;
                            }
                        }
                        if (i3 != length) {
                            i3++;
                        } else {
                            return z6;
                        }
                    }
                }
            }
        }
        return false;
    }

    public final void f() {
        Trace.beginSection("sendAccessibilitySemanticsStructureChangeEvents");
        try {
            if (n()) {
                t(this.h.getSemanticsOwner().a(), this.L);
            }
            Trace.endSection();
            Trace.beginSection("sendSemanticsPropertyChangeEvents");
            try {
                z(k());
                Trace.endSection();
                Trace.beginSection("updateSemanticsNodesCopyAndPanes");
                try {
                    I();
                } finally {
                }
            } finally {
            }
        } finally {
        }
    }

    public final AccessibilityEvent g(int i, int i2) {
        uu0 uu0Var;
        AccessibilityEvent obtain = AccessibilityEvent.obtain(i2);
        obtain.setEnabled(true);
        obtain.setClassName("android.view.View");
        b4 b4Var = this.h;
        obtain.setPackageName(b4Var.getContext().getPackageName());
        obtain.setSource(b4Var, i);
        if (n() && (uu0Var = (uu0) k().b(i)) != null) {
            su0 su0Var = uu0Var.a;
            obtain.setPassword(su0Var.d.e.c(wu0.H));
            Object g = su0Var.d.e.g(wu0.n);
            if (g == null) {
                g = null;
            }
            boolean e = o20.e(g, Boolean.TRUE);
            if (Build.VERSION.SDK_INT >= 34) {
                r0.f(obtain, e);
            }
        }
        return obtain;
    }

    public final AccessibilityEvent h(int i, Integer num, Integer num2, Integer num3, CharSequence charSequence) {
        AccessibilityEvent g = g(i, 8192);
        if (num != null) {
            g.setFromIndex(num.intValue());
        }
        if (num2 != null) {
            g.setToIndex(num2.intValue());
        }
        if (num3 != null) {
            g.setItemCount(num3.intValue());
        }
        if (charSequence != null) {
            g.getText().add(charSequence);
        }
        return g;
    }

    public final int i(su0 su0Var) {
        nu0 nu0Var = su0Var.d;
        if (!nu0Var.e.c(wu0.a)) {
            av0 av0Var = wu0.E;
            if (nu0Var.e.c(av0Var)) {
                return (int) (((m11) nu0Var.d(av0Var)).a & 4294967295L);
            }
        }
        return this.x;
    }

    public final int j(su0 su0Var) {
        nu0 nu0Var = su0Var.d;
        if (!nu0Var.e.c(wu0.a)) {
            av0 av0Var = wu0.E;
            if (nu0Var.e.c(av0Var)) {
                return (int) (((m11) nu0Var.d(av0Var)).a >> 32);
            }
        }
        return this.x;
    }

    public final t10 k() {
        su0 su0Var;
        if (this.B) {
            this.B = false;
            b4 b4Var = this.h;
            this.D = o20.p(b4Var.getSemanticsOwner(), w3.h);
            if (n()) {
                he0 he0Var = this.D;
                Resources resources = b4Var.getContext().getResources();
                fe0 fe0Var = this.F;
                fe0Var.a();
                fe0 fe0Var2 = this.G;
                fe0Var2.a();
                uu0 uu0Var = (uu0) he0Var.b(-1);
                if (uu0Var != null) {
                    su0Var = uu0Var.a;
                } else {
                    su0Var = null;
                }
                su0Var.getClass();
                ArrayList b = dv0.b(su0Var, new q2(4, he0Var), new q2(5, resources), jc0.v(su0Var));
                int i = 1;
                int size = b.size() - 1;
                if (1 <= size) {
                    while (true) {
                        int i2 = ((su0) b.get(i - 1)).f;
                        int i3 = ((su0) b.get(i)).f;
                        fe0Var.f(i2, i3);
                        fe0Var2.f(i3, i2);
                        if (i == size) {
                            break;
                        }
                        i++;
                    }
                }
            }
        }
        return this.D;
    }

    public final wo0 m(su0 su0Var, Rect rect, zv0 zv0Var) {
        f4 f4Var = new f4(zv0Var);
        z40 z40Var = su0Var.c;
        bd0 bd0Var = z40Var.H.f;
        im imVar = null;
        if ((bd0Var.h & 8) != 0) {
            loop0: while (true) {
                if (bd0Var == null) {
                    break;
                }
                if ((bd0Var.g & 8) != 0) {
                    bd0 bd0Var2 = bd0Var;
                    ef0 ef0Var = null;
                    while (bd0Var2 != null) {
                        if (bd0Var2 instanceof qu0) {
                            ((qu0) bd0Var2).f0(f4Var);
                            if (f4Var.e) {
                                imVar = bd0Var2;
                                break loop0;
                            }
                        } else if ((bd0Var2.g & 8) != 0 && (bd0Var2 instanceof jm)) {
                            int i = 0;
                            for (bd0 bd0Var3 = ((jm) bd0Var2).t; bd0Var3 != null; bd0Var3 = bd0Var3.j) {
                                if ((bd0Var3.g & 8) != 0) {
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
                if ((bd0Var.h & 8) == 0) {
                    break;
                }
                bd0Var = bd0Var.j;
            }
        }
        im imVar2 = (qu0) imVar;
        if (imVar2 != null && ((bd0) imVar2).e.r) {
            ng0 D = k81.D(imVar2);
            wo0 U = o30.n(D).U(D, false);
            Rect E = E(U.a, U.b, U.c, U.d);
            float f = E.left - rect.left;
            float f2 = E.top - rect.top;
            return new wo0(f, f2, E.width() + f, E.height() + f2);
        }
        return o30.i(z40Var.H.d, false);
    }

    public final boolean n() {
        AccessibilityManager accessibilityManager = this.k;
        if (accessibilityManager.isEnabled()) {
            List<AccessibilityServiceInfo> list = this.m;
            if (list == null) {
                list = accessibilityManager.getEnabledAccessibilityServiceList(-1);
                this.m = list;
            }
            if (!list.isEmpty()) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final void o(z40 z40Var) {
        if (this.z.add(z40Var)) {
            this.A.q(x31.a);
        }
    }

    @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
    public final void onAccessibilityStateChanged(boolean z) {
        this.m = null;
    }

    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
    public final void onTouchExplorationStateChanged(boolean z) {
        this.m = null;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        AccessibilityManager accessibilityManager = this.k;
        if (accessibilityManager.isEnabled()) {
            this.m = null;
        }
        accessibilityManager.addAccessibilityStateChangeListener(this);
        accessibilityManager.addTouchExplorationStateChangeListener(this);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        Handler handler = this.h.getHandler();
        handler.getClass();
        handler.removeCallbacks(this.O);
        AccessibilityManager accessibilityManager = this.k;
        accessibilityManager.removeAccessibilityStateChangeListener(this);
        accessibilityManager.removeTouchExplorationStateChangeListener(this);
    }

    public final int s(int i) {
        if (i == this.h.getSemanticsOwner().a().f) {
            return -1;
        }
        return i;
    }

    public final void t(su0 su0Var, tu0 tu0Var) {
        int[] iArr = b20.a;
        ie0 ie0Var = new ie0();
        List j = su0.j(4, su0Var);
        z40 z40Var = su0Var.c;
        int size = j.size();
        for (int i = 0; i < size; i++) {
            su0 su0Var2 = (su0) j.get(i);
            t10 k = k();
            int i2 = su0Var2.f;
            if (k.a(i2)) {
                if (!tu0Var.b.b(i2)) {
                    o(z40Var);
                    return;
                }
                ie0Var.a(i2);
            }
        }
        ie0 ie0Var2 = tu0Var.b;
        int[] iArr2 = ie0Var2.b;
        long[] jArr = ie0Var2.a;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i3 = 0;
            while (true) {
                long j2 = jArr[i3];
                if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i4 = 8 - ((~(i3 - length)) >>> 31);
                    for (int i5 = 0; i5 < i4; i5++) {
                        if ((255 & j2) < 128 && !ie0Var.b(iArr2[(i3 << 3) + i5])) {
                            o(z40Var);
                            return;
                        }
                        j2 >>= 8;
                    }
                    if (i4 != 8) {
                        break;
                    }
                }
                if (i3 == length) {
                    break;
                } else {
                    i3++;
                }
            }
        }
        List j3 = su0.j(4, su0Var);
        int size2 = j3.size();
        for (int i6 = 0; i6 < size2; i6++) {
            su0 su0Var3 = (su0) j3.get(i6);
            tu0 tu0Var2 = (tu0) this.K.b(su0Var3.f);
            if (tu0Var2 != null && k().a(su0Var3.f)) {
                t(su0Var3, tu0Var2);
            }
        }
    }

    public final boolean u(AccessibilityEvent accessibilityEvent) {
        if (!n()) {
            return false;
        }
        if (accessibilityEvent.getEventType() == 2048 || accessibilityEvent.getEventType() == 32768) {
            this.s = true;
        }
        try {
            return ((Boolean) this.j.e(accessibilityEvent)).booleanValue();
        } finally {
            this.s = false;
        }
    }

    public final boolean v(int i, int i2, Integer num, List list) {
        if (i != Integer.MIN_VALUE && n()) {
            AccessibilityEvent g = g(i, i2);
            if (num != null) {
                g.setContentChangeTypes(num.intValue());
            }
            if (list != null) {
                g.setContentDescription(ma0.a(list, ",", null, 62));
            }
            return u(g);
        }
        return false;
    }

    public final void x(int i, int i2, String str) {
        AccessibilityEvent g = g(s(i), 32);
        g.setContentChangeTypes(i2);
        if (str != null) {
            g.getText().add(str);
        }
        u(g);
    }

    public final void y(int i) {
        d4 d4Var = this.C;
        if (d4Var != null) {
            su0 su0Var = d4Var.a;
            if (i != su0Var.f) {
                return;
            }
            if (SystemClock.uptimeMillis() - d4Var.f <= 1000) {
                AccessibilityEvent g = g(s(su0Var.f), 131072);
                g.setFromIndex(d4Var.d);
                g.setToIndex(d4Var.e);
                g.setAction(d4Var.b);
                g.setMovementGranularity(d4Var.c);
                g.getText().add(l(su0Var));
                u(g);
            }
        }
        this.C = null;
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    public final void z(defpackage.t10 r57) {
        /*
            Method dump skipped, instructions count: 1646
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.h4.z(t10):void");
    }
}

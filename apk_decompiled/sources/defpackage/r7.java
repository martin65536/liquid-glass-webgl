package defpackage;

import android.content.Context;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Trace;
import android.util.SparseArray;
import com.kyant.backdrop.catalog.R;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class r7 implements jm0, t7 {
    public static volatile r7 i;
    public static final Object j = new Object();
    public final /* synthetic */ int e;
    public Object f;
    public Object g;
    public Object h;

    public r7(int i2) {
        this.e = i2;
        switch (i2) {
            case 3:
                this.f = new j2(5);
                this.g = new j2(5);
                this.h = new j2(5);
                return;
            case 8:
                long[] jArr = zs0.a;
                this.f = new ve0();
                return;
            case 11:
                this.f = new AtomicReference(dl.s);
                this.g = new Object();
                return;
            case 13:
                this.f = new WeakHashMap();
                this.g = new WeakHashMap();
                this.h = new WeakHashMap();
                return;
            default:
                this.h = new ey0(6);
                return;
        }
    }

    public static r7 t(Context context) {
        if (i == null) {
            synchronized (j) {
                try {
                    if (i == null) {
                        i = new r7(context);
                    }
                } finally {
                }
            }
        }
        return i;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:6:0x001c. Please report as an issue. */
    public void A(r7 r7Var, mp0 mp0Var) {
        Exception exc;
        Exception exc2;
        int i2;
        ge0 ge0Var = (ge0) this.f;
        int i3 = ge0Var.b;
        pe0 pe0Var = (pe0) this.g;
        pe0 pe0Var2 = new pe0();
        int i4 = 0;
        int i5 = 0;
        while (i4 < i3) {
            int i6 = i4 + 1;
            try {
                try {
                    switch (ge0Var.b(i4)) {
                        case 0:
                            r7Var.j();
                            i4 = i6;
                        case 1:
                            int i7 = i5 + 1;
                            r7Var.b(pe0Var.f(i5));
                            i5 = i7;
                            i4 = i6;
                        case 2:
                            int i8 = i4 + 2;
                            i4 += 3;
                            r7Var.h(ge0Var.b(i6), ge0Var.b(i8));
                        case 3:
                            int i9 = i4 + 2;
                            try {
                                i2 = i4 + 3;
                            } catch (Exception e) {
                                exc = e;
                                i4 = i9;
                            }
                            try {
                                i4 += 4;
                                r7Var.f(ge0Var.b(i6), ge0Var.b(i9), ge0Var.b(i2));
                            } catch (Exception e2) {
                                exc = e2;
                                i4 = i2;
                                throw new zg(pe0Var, pe0Var2, ge0Var, i4 - 1, exc);
                            }
                        case 4:
                            r7Var.l();
                            i4 = i6;
                        case 5:
                            i4 += 2;
                            int i10 = i5 + 1;
                            r7Var.a(ge0Var.b(i6), pe0Var.f(i5));
                            i5 = i10;
                        case 6:
                            i4 += 2;
                            try {
                                ge0Var.b(i6);
                                int i11 = i5 + 1;
                                i5 = i11;
                            } catch (Exception e3) {
                                exc2 = e3;
                                exc = exc2;
                                throw new zg(pe0Var, pe0Var2, ge0Var, i4 - 1, exc);
                            }
                        case 7:
                            int i12 = i5 + 1;
                            Object f = pe0Var.f(i5);
                            f.getClass();
                            f31.n(2, f);
                            i5 += 2;
                            ((kv) f).d(r7Var.r(), pe0Var.f(i12));
                            i4 = i6;
                        case 8:
                            Object obj = r7Var.h;
                            if (obj instanceof xg) {
                                xg xgVar = (xg) obj;
                                if (mp0Var.f.j(xgVar)) {
                                    xgVar.b();
                                }
                            }
                            pe0Var2.a(obj);
                            r7Var.c();
                            i4 = i6;
                        default:
                            i4 = i6;
                    }
                } catch (Exception e4) {
                    exc2 = e4;
                    i4 = i6;
                    exc = exc2;
                    throw new zg(pe0Var, pe0Var2, ge0Var, i4 - 1, exc);
                }
            } catch (Throwable th) {
                r7Var.e();
                throw th;
            }
        }
        if (i5 != pe0Var.b) {
            rh.a("Applier operation size mismatch");
        }
        pe0Var.d();
        ge0Var.b = 0;
        r7Var.e();
    }

    public Object B(CharSequence charSequence, int i2, int i3, int i4, boolean z, vq vqVar) {
        int i5;
        vc0 vc0Var;
        char c;
        xq xqVar = new xq((vc0) ((e3) this.g).c);
        int codePointAt = Character.codePointAt(charSequence, i2);
        int i6 = 0;
        boolean z2 = true;
        int i7 = i2;
        loop0: while (true) {
            i5 = i7;
            while (i7 < i3 && i6 < i4 && z2) {
                SparseArray sparseArray = xqVar.c.a;
                if (sparseArray == null) {
                    vc0Var = null;
                } else {
                    vc0Var = (vc0) sparseArray.get(codePointAt);
                }
                if (xqVar.a != 2) {
                    if (vc0Var == null) {
                        xqVar.a();
                        c = 1;
                    } else {
                        xqVar.a = 2;
                        xqVar.c = vc0Var;
                        xqVar.f = 1;
                        c = 2;
                    }
                } else {
                    if (vc0Var != null) {
                        xqVar.c = vc0Var;
                        xqVar.f++;
                    } else {
                        if (codePointAt == 65038) {
                            xqVar.a();
                        } else if (codePointAt != 65039) {
                            vc0 vc0Var2 = xqVar.c;
                            if (vc0Var2.b != null) {
                                if (xqVar.f == 1) {
                                    if (xqVar.b()) {
                                        xqVar.d = xqVar.c;
                                        xqVar.a();
                                    } else {
                                        xqVar.a();
                                    }
                                } else {
                                    xqVar.d = vc0Var2;
                                    xqVar.a();
                                }
                                c = 3;
                            } else {
                                xqVar.a();
                            }
                        }
                        c = 1;
                    }
                    c = 2;
                }
                xqVar.e = codePointAt;
                if (c != 1) {
                    if (c != 2) {
                        if (c == 3) {
                            if (z || !w(charSequence, i5, i7, xqVar.d.b)) {
                                z2 = vqVar.h(charSequence, i5, i7, xqVar.d.b);
                                i6++;
                            }
                        }
                    } else {
                        int charCount = Character.charCount(codePointAt) + i7;
                        if (charCount < i3) {
                            codePointAt = Character.codePointAt(charSequence, charCount);
                        }
                        i7 = charCount;
                    }
                } else {
                    i7 = Character.charCount(Character.codePointAt(charSequence, i5)) + i5;
                    if (i7 < i3) {
                        codePointAt = Character.codePointAt(charSequence, i7);
                    }
                }
            }
        }
        if (xqVar.a == 2 && xqVar.c.b != null && ((xqVar.f > 1 || xqVar.b()) && i6 < i4 && z2 && (z || !w(charSequence, i5, i7, xqVar.c.b)))) {
            vqVar.h(charSequence, i5, i7, xqVar.c.b);
        }
        return vqVar.a();
    }

    public void C(Object obj) {
        long o = m20.o();
        if (o == a21.a) {
            this.h = obj;
            return;
        }
        synchronized (this.g) {
            x11 x11Var = (x11) ((AtomicReference) this.f).get();
            int a = x11Var.a(o);
            if (a < 0) {
                ((AtomicReference) this.f).set(x11Var.b(o, obj));
            } else {
                x11Var.c[a] = obj;
            }
        }
    }

    public void D(uc ucVar) {
        ((yc) this.h).e.c = ucVar;
    }

    public void E(mm mmVar) {
        ((yc) this.h).e.a = mmVar;
    }

    public void F(m40 m40Var) {
        ((yc) this.h).e.b = m40Var;
    }

    public void G(long j2) {
        ((yc) this.h).e.d = j2;
    }

    public void H() {
        ve0 ve0Var = (ve0) this.f;
        String str = (String) this.g;
        List list = (List) ve0Var.k(str);
        if (list != null) {
            list.remove((vu) this.h);
        }
        if (list != null && !list.isEmpty()) {
            ve0Var.m(str, list);
        }
    }

    @Override // defpackage.t7
    public void a(int i2, Object obj) {
        switch (this.e) {
            case 9:
                ge0 ge0Var = (ge0) this.f;
                ge0Var.a(5);
                ge0Var.a(i2);
                ((pe0) this.g).a(obj);
                return;
            default:
                ((z40) this.h).y(i2, (z40) obj);
                return;
        }
    }

    @Override // defpackage.t7
    public void b(Object obj) {
        switch (this.e) {
            case 9:
                ((ge0) this.f).a(1);
                ((pe0) this.g).a(obj);
                return;
            default:
                ((ArrayList) this.g).add(this.h);
                this.h = obj;
                return;
        }
    }

    @Override // defpackage.t7
    public void c() {
        yo0 rectManager;
        g3 g3Var;
        yo0 rectManager2;
        switch (this.e) {
            case 9:
                ((ge0) this.f).a(8);
                return;
            default:
                z40 z40Var = (z40) this.h;
                lg0 lg0Var = z40Var.H;
                if (!z40Var.E()) {
                    q00.a("onReuse is only expected on attached node");
                }
                n50 n50Var = z40Var.J;
                if (n50Var != null) {
                    n50Var.i(false);
                }
                z40Var.w = false;
                if (z40Var.Q) {
                    z40Var.Q = false;
                } else {
                    bd0 bd0Var = z40Var.H.e;
                    for (bd0 bd0Var2 = bd0Var; bd0Var2 != null; bd0Var2 = bd0Var2.i) {
                        if (bd0Var2.r) {
                            bd0Var2.y0();
                        }
                    }
                    for (bd0 bd0Var3 = bd0Var; bd0Var3 != null; bd0Var3 = bd0Var3.i) {
                        if (bd0Var3.r) {
                            bd0Var3.A0();
                        }
                    }
                    while (bd0Var != null) {
                        if (bd0Var.r) {
                            bd0Var.s0();
                        }
                        bd0Var = bd0Var.i;
                    }
                }
                int i2 = z40Var.f;
                mj0 mj0Var = z40Var.r;
                if (mj0Var != null && (rectManager2 = ((b4) mj0Var).getRectManager()) != null) {
                    rectManager2.g(z40Var);
                }
                z40Var.f = pu0.a.addAndGet(1);
                mj0 mj0Var2 = z40Var.r;
                if (mj0Var2 != null) {
                    b4 b4Var = (b4) mj0Var2;
                    b4Var.m7getLayoutNodes().g(i2);
                    b4Var.m7getLayoutNodes().h(z40Var.f, z40Var);
                }
                for (bd0 bd0Var4 = lg0Var.f; bd0Var4 != null; bd0Var4 = bd0Var4.j) {
                    bd0Var4.r0();
                }
                lg0Var.e();
                if (lg0Var.d(8)) {
                    z40Var.C();
                }
                z40.U(z40Var);
                mj0 mj0Var3 = z40Var.r;
                if (mj0Var3 != null) {
                    b4 b4Var2 = (b4) mj0Var3;
                    if (b4.k() && (g3Var = b4Var2.Q) != null) {
                        b4 b4Var3 = g3Var.g;
                        j2 j2Var = g3Var.e;
                        ie0 ie0Var = g3Var.k;
                        if (ie0Var.e(i2)) {
                            j2Var.k(b4Var3, i2, false);
                        }
                        nu0 u = z40Var.u();
                        if (u != null && u.e.b(wu0.q)) {
                            ie0Var.a(z40Var.f);
                            j2Var.k(b4Var3, z40Var.f, true);
                        }
                    }
                }
                mj0 mj0Var4 = z40Var.r;
                if (mj0Var4 != null && (rectManager = ((b4) mj0Var4).getRectManager()) != null) {
                    rectManager.f(z40Var);
                    return;
                }
                return;
        }
    }

    @Override // defpackage.t7
    public void d(int i2, Object obj) {
        switch (this.e) {
            case 9:
                ge0 ge0Var = (ge0) this.f;
                ge0Var.a(6);
                ge0Var.a(i2);
                ((pe0) this.g).a(obj);
                return;
            default:
                return;
        }
    }

    @Override // defpackage.t7
    public void e() {
        switch (this.e) {
            case 9:
                return;
            default:
                mj0 mj0Var = ((z40) this.f).r;
                if (mj0Var != null) {
                    ((b4) mj0Var).z();
                    return;
                }
                return;
        }
    }

    @Override // defpackage.t7
    public void f(int i2, int i3, int i4) {
        switch (this.e) {
            case 9:
                ge0 ge0Var = (ge0) this.f;
                ge0Var.a(3);
                ge0Var.a(i2);
                ge0Var.a(i3);
                ge0Var.a(i4);
                return;
            default:
                ((z40) this.h).J(i2, i3, i4);
                return;
        }
    }

    @Override // defpackage.jm0
    public ua0 g() {
        LocaleList localeList;
        int size;
        Locale locale;
        localeList = LocaleList.getDefault();
        synchronized (((ey0) this.h)) {
            try {
                ua0 ua0Var = (ua0) this.g;
                if (ua0Var == null || localeList != ((LocaleList) this.f)) {
                    size = localeList.size();
                    ArrayList arrayList = new ArrayList(size);
                    for (int i2 = 0; i2 < size; i2++) {
                        locale = localeList.get(i2);
                        arrayList.add(new ta0(locale));
                    }
                    ua0 ua0Var2 = new ua0(arrayList);
                    this.f = localeList;
                    this.g = ua0Var2;
                    return ua0Var2;
                }
                return ua0Var;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // defpackage.t7
    public void h(int i2, int i3) {
        switch (this.e) {
            case 9:
                ge0 ge0Var = (ge0) this.f;
                ge0Var.a(2);
                ge0Var.a(i2);
                ge0Var.a(i3);
                return;
            default:
                ((z40) this.h).O(i2, i3);
                return;
        }
    }

    @Override // defpackage.t7
    public void i(kv kvVar, Object obj) {
        switch (this.e) {
            case 9:
                ((ge0) this.f).a(7);
                pe0 pe0Var = (pe0) this.g;
                pe0Var.a(kvVar);
                pe0Var.a(obj);
                return;
            default:
                kvVar.d(r(), obj);
                return;
        }
    }

    @Override // defpackage.t7
    public void j() {
        switch (this.e) {
            case 9:
                ((ge0) this.f).a(0);
                return;
            default:
                this.h = ((ArrayList) this.g).remove(r0.size() - 1);
                return;
        }
    }

    public void k(z40 z40Var, u20 u20Var) {
        j2 j2Var = (j2) this.f;
        j2 j2Var2 = (j2) this.g;
        j2 j2Var3 = (j2) this.h;
        int ordinal = u20Var.ordinal();
        if (ordinal != 0) {
            if (ordinal != 1) {
                if (ordinal != 2) {
                    if (ordinal == 3) {
                        if (z40Var.l != null) {
                            j2Var3.f(z40Var);
                            return;
                        } else {
                            j2Var2.f(z40Var);
                            return;
                        }
                    }
                    v7.k();
                    return;
                }
                if (z40Var.l != null) {
                    j2Var3.f(z40Var);
                    return;
                } else {
                    j2Var.f(z40Var);
                    return;
                }
            }
            j2Var2.f(z40Var);
            j2Var3.f(z40Var);
            return;
        }
        j2Var.f(z40Var);
        j2Var3.f(z40Var);
    }

    public void l() {
        ((ArrayList) this.g).clear();
        this.h = this.f;
        ((z40) this.f).N();
    }

    public boolean m(z40 z40Var) {
        boolean z;
        boolean z2;
        if (z40Var.l == null) {
            z = true;
        } else {
            z = false;
        }
        if (!((rx0) ((j2) this.f).f).contains(z40Var) && !((rx0) ((j2) this.g).f).contains(z40Var)) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z || !z2) {
            return false;
        }
        return true;
    }

    public void n(Bundle bundle) {
        HashSet hashSet = (HashSet) this.g;
        String string = ((Context) this.h).getString(R.string.androidx_startup);
        if (bundle != null) {
            try {
                HashSet hashSet2 = new HashSet();
                for (String str : bundle.keySet()) {
                    if (string.equals(bundle.getString(str, null))) {
                        Class<?> cls = Class.forName(str);
                        if (n00.class.isAssignableFrom(cls)) {
                            hashSet.add(cls);
                        }
                    }
                }
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    o((Class) it.next(), hashSet2);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Object o(Class cls, HashSet hashSet) {
        Object obj;
        HashMap hashMap = (HashMap) this.f;
        if (n30.w()) {
            try {
                n30.f(cls.getSimpleName());
            } finally {
                Trace.endSection();
            }
        }
        if (!hashSet.contains(cls)) {
            if (!hashMap.containsKey(cls)) {
                hashSet.add(cls);
                try {
                    n00 n00Var = (n00) cls.getDeclaredConstructor(null).newInstance(null);
                    List<Class> a = n00Var.a();
                    if (!a.isEmpty()) {
                        for (Class cls2 : a) {
                            if (!hashMap.containsKey(cls2)) {
                                o(cls2, hashSet);
                            }
                        }
                    }
                    obj = n00Var.b((Context) this.h);
                    hashSet.remove(cls);
                    hashMap.put(cls, obj);
                } catch (Throwable th) {
                    throw new RuntimeException(th);
                }
            } else {
                obj = hashMap.get(cls);
            }
            return obj;
        }
        throw new IllegalStateException("Cannot initialize " + cls.getName() + ". Cycle detected.");
    }

    public Object p() {
        long o = m20.o();
        if (o == a21.a) {
            return this.h;
        }
        x11 x11Var = (x11) ((AtomicReference) this.f).get();
        int a = x11Var.a(o);
        if (a >= 0) {
            return x11Var.c[a];
        }
        return null;
    }

    public uc q() {
        return ((yc) this.h).e.c;
    }

    public Object r() {
        return this.h;
    }

    public mm s() {
        return ((yc) this.h).e.a;
    }

    public m40 u() {
        return ((yc) this.h).e.b;
    }

    public long v() {
        return ((yc) this.h).e.d;
    }

    public boolean w(CharSequence charSequence, int i2, int i3, n31 n31Var) {
        int i4;
        if ((n31Var.c & 3) == 0) {
            tl tlVar = (tl) this.h;
            tc0 b = n31Var.b();
            int a = b.a(8);
            if (a != 0) {
                ((ByteBuffer) b.h).getShort(a + b.e);
            }
            tlVar.getClass();
            ThreadLocal threadLocal = tl.b;
            if (threadLocal.get() == null) {
                threadLocal.set(new StringBuilder());
            }
            StringBuilder sb = (StringBuilder) threadLocal.get();
            sb.setLength(0);
            while (i2 < i3) {
                sb.append(charSequence.charAt(i2));
                i2++;
            }
            boolean hasGlyph = tlVar.a.hasGlyph(sb.toString());
            int i5 = n31Var.c & 4;
            if (hasGlyph) {
                i4 = i5 | 2;
            } else {
                i4 = i5 | 1;
            }
            n31Var.c = i4;
        }
        if ((n31Var.c & 3) != 2) {
            return false;
        }
        return true;
    }

    public boolean x() {
        boolean z;
        if (((rx0) ((j2) this.f).f).isEmpty() && ((rx0) ((j2) this.h).f).isEmpty() && ((rx0) ((j2) this.g).f).isEmpty()) {
            z = true;
        } else {
            z = false;
        }
        return !z;
    }

    public boolean y() {
        if (((hy0) this.f).getValue() == this.h) {
            r7 r7Var = (r7) this.g;
            if (r7Var == null || !r7Var.y()) {
                return false;
            }
            return true;
        }
        return true;
    }

    private final /* synthetic */ void z() {
    }

    public r7(no0 no0Var) {
        this.e = 7;
        this.f = new AtomicInteger(0);
        this.g = new a9();
        this.h = new f9(5, this, no0Var);
    }

    public /* synthetic */ r7(Object obj, Object obj2, Object obj3, int i2) {
        this.e = i2;
        this.f = obj;
        this.g = obj2;
        this.h = obj3;
    }

    public r7(yc ycVar) {
        this.e = 2;
        this.h = ycVar;
        this.f = new j2(2, this);
    }

    public r7(Context context) {
        this.e = 0;
        this.h = context.getApplicationContext();
        this.g = new HashSet();
        this.f = new HashMap();
    }

    public r7(e3 e3Var, dt0 dt0Var, tl tlVar, Set set) {
        this.e = 4;
        this.f = dt0Var;
        this.g = e3Var;
        this.h = tlVar;
        if (set.isEmpty()) {
            return;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            int[] iArr = (int[]) it.next();
            String str = new String(iArr, 0, iArr.length);
            B(str, 0, str.length(), 1, true, new wq(str, 0));
        }
    }

    public r7(q31 q31Var, r7 r7Var) {
        this.e = 12;
        this.f = q31Var;
        this.g = r7Var;
        this.h = q31Var.e;
    }

    public r7(z40 z40Var) {
        this.e = 14;
        this.f = z40Var;
        this.g = new ArrayList();
        this.h = z40Var;
    }

    public r7(Object obj) {
        this.e = 9;
        this.f = new ge0();
        this.g = new pe0();
        this.h = obj;
    }
}

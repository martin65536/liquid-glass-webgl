package defpackage;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import java.util.Collections;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j10 extends g61 implements Runnable, fh0, View.OnAttachStateChangeListener {
    public boolean g;
    public int h;
    public k71 i;
    public final ve0 j;
    public final fk0 k;
    public final pe0 l;
    public final mx0 m;

    public j10() {
        super(1);
        ve0 ve0Var = new ve0(9);
        n71.a.getClass();
        ve0Var.m(m71.b, new c81("caption bar"));
        ve0Var.m(m71.c, new c81("display cutout"));
        ve0Var.m(m71.d, new c81("ime"));
        ve0Var.m(m71.e, new c81("mandatory system gestures"));
        ve0Var.m(m71.f, new c81("navigation bars"));
        ve0Var.m(m71.g, new c81("status bars"));
        ve0Var.m(m71.h, new c81("system gestures"));
        ve0Var.m(m71.i, new c81("tappable element"));
        ve0Var.m(m71.j, new c81("waterfall"));
        this.j = ve0Var;
        this.k = new fk0(0);
        this.l = new pe0(4);
        this.m = new mx0();
    }

    @Override // defpackage.fh0
    public final k71 a(View view, k71 k71Var) {
        if (this.g) {
            this.i = k71Var;
            if (Build.VERSION.SDK_INT == 30) {
                view.post(this);
                return k71Var;
            }
        } else if (this.h == 0) {
            f(k71Var);
        }
        return k71Var;
    }

    @Override // defpackage.g61
    public final void b(p61 p61Var) {
        boolean z = false;
        this.g = false;
        int d = p61Var.a.d();
        this.h &= ~d;
        this.i = null;
        n71 n71Var = (n71) p71.a.b(d);
        if (n71Var != null) {
            Object g = this.j.g(n71Var);
            g.getClass();
            c81 c81Var = (c81) g;
            c81Var.c.h(0.0f);
            c81Var.e.h(1.0f);
            c81Var.d.g(0L);
            c81Var.c.h(0.0f);
            c81Var.b.setValue(Boolean.FALSE);
            c81Var.j = -1L;
            c81Var.k = -1L;
            fk0 fk0Var = this.k;
            fk0Var.h(fk0Var.g() + 1);
            synchronized (cx0.c) {
                we0 we0Var = cx0.j.h;
                if (we0Var != null) {
                    if (we0Var.h()) {
                        z = true;
                    }
                }
            }
            if (z) {
                cx0.a();
            }
        }
    }

    @Override // defpackage.g61
    public final void c() {
        this.g = true;
    }

    @Override // defpackage.g61
    public final k71 d(k71 k71Var, List list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            p61 p61Var = (p61) list.get(i);
            n71 n71Var = (n71) p71.a.b(p61Var.a.d());
            if (n71Var != null) {
                Object g = this.j.g(n71Var);
                g.getClass();
                c81 c81Var = (c81) g;
                if (((Boolean) c81Var.b.getValue()).booleanValue()) {
                    o61 o61Var = p61Var.a;
                    c81Var.c.h(o61Var.c());
                    c81Var.e.h(o61Var.a());
                    c81Var.d.g(o61Var.b());
                }
            }
        }
        f(k71Var);
        return k71Var;
    }

    @Override // defpackage.g61
    public final c4 e(p61 p61Var, c4 c4Var) {
        k71 k71Var = this.i;
        boolean z = false;
        this.g = false;
        this.i = null;
        if (p61Var.a.b() > 0 && k71Var != null) {
            int d = p61Var.a.d();
            this.h |= d;
            n71 n71Var = (n71) p71.a.b(d);
            if (n71Var != null) {
                Object g = this.j.g(n71Var);
                g.getClass();
                c81 c81Var = (c81) g;
                g10 h = k71Var.a.h(d);
                long j = (h.a << 48) | (h.b << 32) | (h.c << 16) | h.d;
                long j2 = c81Var.h;
                if (!y20.g(j, j2)) {
                    c81Var.j = j2;
                    c81Var.k = j;
                    c81Var.b.setValue(Boolean.TRUE);
                    o61 o61Var = p61Var.a;
                    c81Var.c.h(o61Var.c());
                    c81Var.e.h(o61Var.a());
                    c81Var.d.g(o61Var.b());
                    fk0 fk0Var = this.k;
                    fk0Var.h(fk0Var.g() + 1);
                    synchronized (cx0.c) {
                        we0 we0Var = cx0.j.h;
                        if (we0Var != null) {
                            if (we0Var.h()) {
                                z = true;
                            }
                        }
                    }
                    if (z) {
                        cx0.a();
                        return c4Var;
                    }
                }
            }
        }
        return c4Var;
    }

    public final void f(k71 k71Var) {
        char c;
        char c2;
        boolean z;
        char c3;
        boolean z2;
        boolean z3;
        long j;
        List list;
        boolean z4;
        long[] jArr;
        int[] iArr;
        Object[] objArr;
        long[] jArr2;
        int[] iArr2;
        Object[] objArr2;
        long j2;
        int i;
        he0 he0Var = p71.a;
        int[] iArr3 = he0Var.b;
        Object[] objArr3 = he0Var.c;
        long[] jArr3 = he0Var.a;
        int length = jArr3.length - 2;
        if (length >= 0) {
            int i2 = 0;
            z2 = false;
            z3 = false;
            c = 16;
            c2 = ' ';
            while (true) {
                long j3 = jArr3[i2];
                z = true;
                if ((((~j3) << 7) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i3 = 8;
                    int i4 = 8 - ((~(i2 - length)) >>> 31);
                    int i5 = 0;
                    c3 = '0';
                    while (i5 < i4) {
                        if ((j3 & 255) < 128) {
                            int i6 = (i2 << 3) + i5;
                            int i7 = iArr3[i6];
                            n71 n71Var = (n71) objArr3[i6];
                            g10 h = k71Var.a.h(i7);
                            jArr2 = jArr3;
                            iArr2 = iArr3;
                            long j4 = (h.a << 48) | (h.b << 32) | (h.c << 16) | h.d;
                            Object g = this.j.g(n71Var);
                            g.getClass();
                            c81 c81Var = (c81) g;
                            j2 = j3;
                            if (!y20.g(j4, c81Var.h)) {
                                c81Var.h = j4;
                                z2 = true;
                                if (!y20.g(j4, 0L)) {
                                    z3 = true;
                                }
                            }
                            if (i7 != 8) {
                                g10 i8 = k71Var.a.i(i7);
                                objArr2 = objArr3;
                                long j5 = (i8.b << 32) | (i8.a << 48) | (i8.c << 16) | i8.d;
                                if (!y20.g(c81Var.i, j5)) {
                                    c81Var.i = j5;
                                    z2 = true;
                                    if (!y20.g(j5, 0L)) {
                                        z3 = true;
                                    }
                                }
                            } else {
                                objArr2 = objArr3;
                            }
                            c81Var.a.setValue(Boolean.valueOf(k71Var.a.s(i7)));
                            i = 8;
                        } else {
                            jArr2 = jArr3;
                            iArr2 = iArr3;
                            objArr2 = objArr3;
                            j2 = j3;
                            i = i3;
                        }
                        j3 = j2 >> i;
                        i5++;
                        i3 = i;
                        objArr3 = objArr2;
                        jArr3 = jArr2;
                        iArr3 = iArr2;
                    }
                    jArr = jArr3;
                    iArr = iArr3;
                    objArr = objArr3;
                    if (i4 != i3) {
                        break;
                    }
                } else {
                    jArr = jArr3;
                    iArr = iArr3;
                    objArr = objArr3;
                    c3 = '0';
                }
                if (i2 == length) {
                    break;
                }
                i2++;
                objArr3 = objArr;
                jArr3 = jArr;
                iArr3 = iArr;
            }
        } else {
            c = 16;
            c2 = ' ';
            z = true;
            c3 = '0';
            z2 = false;
            z3 = false;
        }
        on g2 = k71Var.a.g();
        if (g2 == null) {
            j = 0;
        } else {
            g10 a = g2.a();
            j = (a.a << c3) | (a.b << c2) | (a.c << c) | a.d;
        }
        ve0 ve0Var = this.j;
        n71.a.getClass();
        Object g3 = ve0Var.g(m71.j);
        g3.getClass();
        c81 c81Var2 = (c81) g3;
        c81Var2.a.setValue(Boolean.valueOf(!y20.g(j, 0L)));
        if (!y20.g(c81Var2.h, j)) {
            c81Var2.h = j;
            c81Var2.i = j;
            z2 = z;
            if (!y20.g(j, 0L)) {
                z3 = z2;
            }
        }
        if (g2 == null) {
            pe0 pe0Var = this.l;
            if (pe0Var.b > 0) {
                pe0Var.d();
                this.m.clear();
                z2 = z;
            }
        } else {
            if (Build.VERSION.SDK_INT >= 28) {
                list = nn.b(g2.a);
            } else {
                list = Collections.EMPTY_LIST;
            }
            int size = list.size();
            pe0 pe0Var2 = this.l;
            if (size < pe0Var2.b) {
                pe0Var2.l(list.size(), this.l.b);
                this.m.d(list.size(), this.m.size());
                z2 = z;
            } else {
                int size2 = list.size() - this.l.b;
                int i9 = 0;
                while (i9 < size2) {
                    pe0 pe0Var3 = this.l;
                    pe0Var3.a(n30.B(list.get(pe0Var3.b)));
                    this.m.add(new x00("display cutout rect " + this.l.b));
                    i9++;
                    z2 = z;
                }
            }
            int size3 = list.size();
            for (int i10 = 0; i10 < size3; i10++) {
                Rect rect = (Rect) list.get(i10);
                af0 af0Var = (af0) this.l.f(i10);
                if (!o20.e(af0Var.getValue(), rect)) {
                    af0Var.setValue(rect);
                    z2 = z;
                }
            }
            if (!list.isEmpty()) {
                z3 = z;
            }
        }
        if ((z3 || this.k.g() != 0) && z2) {
            fk0 fk0Var = this.k;
            fk0Var.h(fk0Var.g() + 1);
            synchronized (cx0.c) {
                we0 we0Var = cx0.j.h;
                if (we0Var != null) {
                    boolean z5 = z;
                    if (we0Var.h() == z5) {
                        z4 = z5;
                    }
                }
                z4 = false;
            }
            if (z4) {
                cx0.a();
            }
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        View view2;
        Object parent = view.getParent();
        if (parent instanceof View) {
            view2 = (View) parent;
        } else {
            view2 = null;
        }
        if (view2 != null) {
            view = view2;
        }
        int i = j51.a;
        e51.b(view, this);
        j51.a(view, this);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        View view2;
        Object parent = view.getParent();
        if (parent instanceof View) {
            view2 = (View) parent;
        } else {
            view2 = null;
        }
        if (view2 != null) {
            view = view2;
        }
        int i = j51.a;
        e51.b(view, null);
        j51.a(view, null);
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.g) {
            this.h = 0;
            this.g = false;
            k71 k71Var = this.i;
            if (k71Var != null) {
                f(k71Var);
                this.i = null;
            }
        }
    }
}

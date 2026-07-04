package defpackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wf0 {
    public final ky0 a = o20.c(xf0.a);
    public final ky0 b;
    public final ko0 c;
    public final a8 d;
    public final a8 e;
    public tf0 f;
    public int g;
    public vf0 h;
    public final LinkedHashSet i;
    public final LinkedHashSet j;
    public final LinkedHashSet k;
    public boolean l;
    public boolean m;
    public boolean n;

    public wf0() {
        ky0 c = o20.c(new uf0());
        this.b = c;
        this.c = new ko0(c);
        this.d = new a8();
        this.e = new a8();
        this.i = new LinkedHashSet();
        this.j = new LinkedHashSet();
        this.k = new LinkedHashSet();
    }

    public final void a(e3 e3Var, vf0 vf0Var, int i) {
        LinkedHashSet linkedHashSet;
        boolean z;
        e3Var.getClass();
        if (vf0Var.a == null) {
            if (i != 0) {
                if (i != 1) {
                    linkedHashSet = this.i;
                } else {
                    linkedHashSet = this.j;
                }
            } else {
                linkedHashSet = this.k;
            }
            linkedHashSet.add(vf0Var);
            vf0Var.a = e3Var;
            ((uf0) this.c.e.getValue()).getClass();
            if (i != 0) {
                if (i != 1) {
                    z = this.n;
                } else {
                    z = this.l;
                }
            } else {
                z = this.m;
            }
            vf0Var.b(z);
            return;
        }
        StringBuilder sb = new StringBuilder("Input '");
        sb.append(vf0Var);
        e3 e3Var2 = vf0Var.a;
        sb.append("' is already added to dispatcher ");
        sb.append(e3Var2);
        sb.append('.');
        throw new IllegalArgumentException(sb.toString().toString());
    }

    public final void b() {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        uf0 uf0Var;
        boolean z6 = true;
        a8 a8Var = this.d;
        if (a8Var == null || !a8Var.isEmpty()) {
            Iterator it = a8Var.iterator();
            while (it.hasNext()) {
                if (((tf0) it.next()).b) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        a8 a8Var2 = this.e;
        if (a8Var2 == null || !a8Var2.isEmpty()) {
            Iterator it2 = a8Var2.iterator();
            while (it2.hasNext()) {
                if (((tf0) it2.next()).b) {
                    z2 = true;
                    break;
                }
            }
        }
        z2 = false;
        if (!z && !z2) {
            z3 = false;
        } else {
            z3 = true;
        }
        if (this.m != z) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (this.l != z2) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (this.n == z3) {
            z6 = false;
        }
        LinkedHashSet linkedHashSet = this.k;
        if (z4) {
            Iterator it3 = linkedHashSet.iterator();
            while (it3.hasNext()) {
                ((vf0) it3.next()).b(z);
            }
        }
        LinkedHashSet linkedHashSet2 = this.j;
        if (z5) {
            Iterator it4 = linkedHashSet2.iterator();
            while (it4.hasNext()) {
                ((vf0) it4.next()).b(z2);
            }
        }
        LinkedHashSet linkedHashSet3 = this.i;
        if (z6) {
            Iterator it5 = linkedHashSet3.iterator();
            while (it5.hasNext()) {
                ((vf0) it5.next()).b(z3);
            }
        }
        this.m = z;
        this.l = z2;
        this.n = z3;
        tf0 tf0Var = this.f;
        if (tf0Var == null) {
            tf0Var = c(0);
        }
        tf0 tf0Var2 = this.f;
        if (tf0Var2 == null) {
            tf0Var2 = c(0);
        }
        if (o20.e(tf0Var2, tf0Var)) {
            if (tf0Var2 == null) {
                uf0Var = new uf0();
            } else {
                ArrayList arrayList = new ArrayList();
                Iterator<E> it6 = a8Var.iterator();
                while (it6.hasNext()) {
                    boolean z7 = ((tf0) it6.next()).b;
                }
                Iterator<E> it7 = a8Var2.iterator();
                while (it7.hasNext()) {
                    boolean z8 = ((tf0) it7.next()).b;
                }
                t20 t20Var = tf0Var2.a;
                ka0 ka0Var = new ka0(10);
                re.P(ka0Var, arrayList);
                ka0Var.add(t20Var);
                re.P(ka0Var, er.e);
                uf0Var = new uf0(arrayList.size(), jc0.j(ka0Var));
            }
            ky0 ky0Var = this.b;
            if (!o20.e((uf0) ky0Var.getValue(), uf0Var)) {
                ky0Var.j(null, uf0Var);
                Iterator it8 = linkedHashSet.iterator();
                while (it8.hasNext()) {
                    ((vf0) it8.next()).getClass();
                }
                Iterator it9 = linkedHashSet2.iterator();
                while (it9.hasNext()) {
                    ((vf0) it9.next()).getClass();
                }
                Iterator it10 = linkedHashSet3.iterator();
                while (it10.hasNext()) {
                    ((vf0) it10.next()).getClass();
                }
            }
        }
    }

    public final tf0 c(int i) {
        Object obj;
        Object obj2;
        a8 a8Var = this.e;
        a8 a8Var2 = this.d;
        Object obj3 = null;
        if (i != -1) {
            if (i != 0) {
                if (i == 1) {
                    Iterator it = a8Var2.iterator();
                    while (it.hasNext()) {
                        ((tf0) it.next()).getClass();
                    }
                    Iterator it2 = a8Var.iterator();
                    while (it2.hasNext()) {
                        ((tf0) it2.next()).getClass();
                    }
                    return null;
                }
                throw new IllegalStateException(("Unsupported direction: '" + i + "'.").toString());
            }
            Iterator it3 = a8Var2.iterator();
            while (true) {
                if (it3.hasNext()) {
                    obj2 = it3.next();
                    if (((tf0) obj2).b) {
                        break;
                    }
                } else {
                    obj2 = null;
                    break;
                }
            }
            tf0 tf0Var = (tf0) obj2;
            if (tf0Var == null) {
                Iterator it4 = a8Var.iterator();
                while (true) {
                    if (!it4.hasNext()) {
                        break;
                    }
                    Object next = it4.next();
                    if (((tf0) next).b) {
                        obj3 = next;
                        break;
                    }
                }
                return (tf0) obj3;
            }
            return tf0Var;
        }
        Iterator it5 = a8Var2.iterator();
        while (true) {
            if (it5.hasNext()) {
                obj = it5.next();
                if (((tf0) obj).b) {
                    break;
                }
            } else {
                obj = null;
                break;
            }
        }
        tf0 tf0Var2 = (tf0) obj;
        if (tf0Var2 == null) {
            Iterator it6 = a8Var.iterator();
            while (true) {
                if (!it6.hasNext()) {
                    break;
                }
                Object next2 = it6.next();
                if (((tf0) next2).b) {
                    obj3 = next2;
                    break;
                }
            }
            return (tf0) obj3;
        }
        return tf0Var2;
    }
}

package defpackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tr0 extends jj implements ps {
    public final ps h;
    public final yj i;
    public final int j;
    public yj k;
    public ij l;

    public tr0(ps psVar, yj yjVar) {
        super(pf.g, cr.e);
        this.h = psVar;
        this.i = yjVar;
        this.j = ((Number) yjVar.n(new w4(10, (byte) 0), 0)).intValue();
    }

    @Override // defpackage.s9, defpackage.jk
    public final jk f() {
        ij ijVar = this.l;
        if (ijVar instanceof jk) {
            return (jk) ijVar;
        }
        return null;
    }

    @Override // defpackage.ps
    public final Object g(Object obj, ij ijVar) {
        try {
            Object m = m(ijVar, obj);
            if (m == ik.e) {
                return m;
            }
            return x31.a;
        } catch (Throwable th) {
            this.k = new co(ijVar.r(), th);
            throw th;
        }
    }

    @Override // defpackage.s9
    public final StackTraceElement j() {
        return null;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        Throwable a = kq0.a(obj);
        if (a != null) {
            this.k = new co(r(), a);
        }
        ij ijVar = this.l;
        if (ijVar != null) {
            ijVar.u(obj);
        }
        return ik.e;
    }

    public final Object m(ij ijVar, Object obj) {
        Comparable comparable;
        int i;
        String str;
        yj r = ijVar.r();
        g30.p(r);
        yj yjVar = this.k;
        if (yjVar != r) {
            int i2 = 0;
            if (!(yjVar instanceof co)) {
                if (((Number) r.n(new wa(7, this), 0)).intValue() == this.j) {
                    this.k = r;
                } else {
                    throw new IllegalStateException(("Flow invariant is violated:\n\t\tFlow was collected in " + this.i + ",\n\t\tbut emission happened in " + r + ".\n\t\tPlease refer to 'flow' documentation or use 'flowOn' instead").toString());
                }
            } else {
                String str2 = "\n            Flow exception transparency is violated:\n                Previous 'emit' call has thrown exception " + ((co) yjVar).f + ", but then emission attempt of value '" + obj + "' has been detected.\n                Emissions from 'catch' blocks are prohibited in order to avoid unspecified behaviour, 'Flow.catch' operator can be used instead.\n                For a more detailed explanation, please refer to Flow documentation.\n            ";
                List C = uy0.C(str2);
                ArrayList arrayList = new ArrayList();
                for (Object obj2 : C) {
                    if (!uy0.B((String) obj2)) {
                        arrayList.add(obj2);
                    }
                }
                ArrayList arrayList2 = new ArrayList(ne.N(arrayList));
                int size = arrayList.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj3 = arrayList.get(i3);
                    i3++;
                    String str3 = (String) obj3;
                    int length = str3.length();
                    int i4 = 0;
                    while (true) {
                        if (i4 < length) {
                            if (!k81.A(str3.charAt(i4))) {
                                break;
                            }
                            i4++;
                        } else {
                            i4 = -1;
                            break;
                        }
                    }
                    if (i4 == -1) {
                        i4 = str3.length();
                    }
                    arrayList2.add(Integer.valueOf(i4));
                }
                Iterator it = arrayList2.iterator();
                if (!it.hasNext()) {
                    comparable = null;
                } else {
                    comparable = (Comparable) it.next();
                    while (it.hasNext()) {
                        Comparable comparable2 = (Comparable) it.next();
                        if (comparable.compareTo(comparable2) > 0) {
                            comparable = comparable2;
                        }
                    }
                }
                Integer num = (Integer) comparable;
                if (num != null) {
                    i = num.intValue();
                } else {
                    i = 0;
                }
                int length2 = str2.length();
                C.size();
                int size2 = C.size() - 1;
                ArrayList arrayList3 = new ArrayList();
                for (Object obj4 : C) {
                    int i5 = i2 + 1;
                    if (i2 >= 0) {
                        String str4 = (String) obj4;
                        if ((i2 == 0 || i2 == size2) && uy0.B(str4)) {
                            str = null;
                        } else {
                            str4.getClass();
                            if (i >= 0) {
                                int length3 = str4.length();
                                if (i <= length3) {
                                    length3 = i;
                                }
                                str = str4.substring(length3);
                            } else {
                                v7.h("Requested character count ", i, " is less than zero.");
                                return null;
                            }
                        }
                        if (str != null) {
                            arrayList3.add(str);
                        }
                        i2 = i5;
                    } else {
                        jc0.H();
                        throw null;
                    }
                }
                StringBuilder sb = new StringBuilder(length2);
                me.U(arrayList3, sb, "\n", "", "", "...", null);
                throw new IllegalStateException(sb.toString().toString());
            }
        }
        this.l = ijVar;
        lv lvVar = vr0.a;
        ps psVar = this.h;
        psVar.getClass();
        Object c = lvVar.c(psVar, obj, this);
        if (!o20.e(c, ik.e)) {
            this.l = null;
        }
        return c;
    }

    @Override // defpackage.jj, defpackage.ij
    public final yj r() {
        yj yjVar = this.k;
        if (yjVar == null) {
            return cr.e;
        }
        return yjVar;
    }
}

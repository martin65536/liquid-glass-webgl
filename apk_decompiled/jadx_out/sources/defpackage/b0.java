package defpackage;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class b0 extends w {
    public abstract b0 b(int i, Object obj);

    public abstract b0 c(Object obj);

    @Override // defpackage.m, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        if (indexOf(obj) != -1) {
            return true;
        }
        return false;
    }

    @Override // defpackage.m, java.util.Collection, java.util.List
    public final boolean containsAll(Collection collection) {
        Collection collection2 = collection;
        if ((collection2 instanceof Collection) && collection2.isEmpty()) {
            return true;
        }
        Iterator it = collection2.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public b0 d(Collection collection) {
        yl0 e = e();
        e.addAll(collection);
        return e.c();
    }

    public abstract yl0 e();

    public abstract b0 f(a0 a0Var);

    public abstract b0 g(int i);

    public abstract b0 h(int i, Object obj);

    @Override // defpackage.w, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return listIterator(0);
    }

    @Override // defpackage.w, java.util.List
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // defpackage.w, java.util.List
    public final List subList(int i, int i2) {
        return new qz(this, i, i2);
    }
}

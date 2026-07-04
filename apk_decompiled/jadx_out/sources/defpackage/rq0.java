package defpackage;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rq0 extends w {
    public final List e;

    public rq0(List list) {
        list.getClass();
        this.e = list;
    }

    @Override // defpackage.m
    public final int a() {
        return this.e.size();
    }

    @Override // java.util.List
    public final Object get(int i) {
        if (i >= 0 && i <= jc0.q(this)) {
            return this.e.get(jc0.q(this) - i);
        }
        throw new IndexOutOfBoundsException("Element index " + i + " must be in range [" + new w10(0, jc0.q(this), 1) + "].");
    }

    @Override // defpackage.w, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return new qq0(this, 0);
    }

    @Override // defpackage.w, java.util.List
    public final ListIterator listIterator() {
        return new qq0(this, 0);
    }

    @Override // defpackage.w, java.util.List
    public final ListIterator listIterator(int i) {
        return new qq0(this, i);
    }
}

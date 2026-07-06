package defpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rw0 implements vh, Iterable, q30 {
    public int f;
    public int h;
    public int i;
    public boolean k;
    public int l;
    public HashMap n;
    public he0 o;
    public int[] e = new int[0];
    public Object[] g = new Object[0];
    public final Object j = new Object();
    public ArrayList m = new ArrayList();

    public final int a(wv wvVar) {
        if (this.k) {
            rh.a("Use active SlotWriter to determine anchor location instead");
        }
        if (!wvVar.a()) {
            cn0.a("Anchor refers to a group that was removed");
        }
        return wvVar.a;
    }

    public final void b() {
        this.n = new HashMap();
    }

    public final qw0 c() {
        if (!this.k) {
            this.i++;
            return new qw0(this);
        }
        v7.o("Cannot read while a writer is pending");
        return null;
    }

    public final uw0 d() {
        if (this.k) {
            rh.a("Cannot start a writer when another writer is pending");
        }
        if (this.i > 0) {
            rh.a("Cannot start a writer when a reader is pending");
        }
        this.k = true;
        this.l++;
        return new uw0(this);
    }

    public final boolean e(wv wvVar) {
        int e;
        if (wvVar.a() && (e = tw0.e(this.m, wvVar.a, this.f)) >= 0 && o20.e(this.m.get(e), wvVar)) {
            return true;
        }
        return false;
    }

    public final dw f(int i) {
        wv wvVar;
        int i2;
        ArrayList arrayList;
        int e;
        HashMap hashMap = this.n;
        if (hashMap != null) {
            if (this.k) {
                rh.a("use active SlotWriter to crate an anchor for location instead");
            }
            if (i >= 0 && i < (i2 = this.f) && (e = tw0.e((arrayList = this.m), i, i2)) >= 0) {
                wvVar = (wv) arrayList.get(e);
            } else {
                wvVar = null;
            }
            if (wvVar != null) {
                return (dw) hashMap.get(wvVar);
            }
        }
        return null;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new ux(this, 0, this.f);
    }
}

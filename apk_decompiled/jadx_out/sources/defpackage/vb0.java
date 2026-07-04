package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vb0 {
    public final int a;
    public final wb0 b;
    public final rt c;
    public int d;
    public int e;
    public int f;

    public vb0(int i) {
        this.a = i;
        if (i > 0) {
            this.b = new wb0(0);
            this.c = new rt(9);
        } else {
            v7.m("maxSize <= 0");
            throw null;
        }
    }

    public final Object a(Object obj) {
        synchronized (this.c) {
            wb0 wb0Var = this.b;
            wb0Var.getClass();
            Object obj2 = wb0Var.e.get(obj);
            if (obj2 != null) {
                this.e++;
                return obj2;
            }
            this.f++;
            return null;
        }
    }

    public final Object b(Object obj, Object obj2) {
        Object put;
        obj.getClass();
        synchronized (this.c) {
            this.d++;
            wb0 wb0Var = this.b;
            wb0Var.getClass();
            put = wb0Var.e.put(obj, obj2);
            if (put != null) {
                this.d--;
            }
        }
        d(this.a);
        return put;
    }

    public final Object c(Object obj) {
        Object remove;
        synchronized (this.c) {
            wb0 wb0Var = this.b;
            wb0Var.getClass();
            remove = wb0Var.e.remove(obj);
            if (remove != null) {
                this.d--;
            }
        }
        return remove;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0087, code lost:
    
        throw new java.lang.IllegalStateException("LruCache.sizeOf() is reporting inconsistent results!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void d(int r5) {
        /*
            r4 = this;
        L0:
            rt r0 = r4.c
            monitor-enter(r0)
            int r1 = r4.d     // Catch: java.lang.Throwable -> L16
            if (r1 < 0) goto L80
            wb0 r1 = r4.b     // Catch: java.lang.Throwable -> L16
            java.util.LinkedHashMap r1 = r1.e     // Catch: java.lang.Throwable -> L16
            boolean r1 = r1.isEmpty()     // Catch: java.lang.Throwable -> L16
            if (r1 == 0) goto L19
            int r1 = r4.d     // Catch: java.lang.Throwable -> L16
            if (r1 != 0) goto L80
            goto L19
        L16:
            r4 = move-exception
            goto L88
        L19:
            int r1 = r4.d     // Catch: java.lang.Throwable -> L16
            if (r1 <= r5) goto L7e
            wb0 r1 = r4.b     // Catch: java.lang.Throwable -> L16
            java.util.LinkedHashMap r1 = r1.e     // Catch: java.lang.Throwable -> L16
            boolean r1 = r1.isEmpty()     // Catch: java.lang.Throwable -> L16
            if (r1 == 0) goto L28
            goto L7e
        L28:
            wb0 r1 = r4.b     // Catch: java.lang.Throwable -> L16
            java.util.LinkedHashMap r1 = r1.e     // Catch: java.lang.Throwable -> L16
            java.util.Set r1 = r1.entrySet()     // Catch: java.lang.Throwable -> L16
            r1.getClass()     // Catch: java.lang.Throwable -> L16
            java.lang.Iterable r1 = (java.lang.Iterable) r1     // Catch: java.lang.Throwable -> L16
            boolean r2 = r1 instanceof java.util.List     // Catch: java.lang.Throwable -> L16
            r3 = 0
            if (r2 == 0) goto L49
            java.util.List r1 = (java.util.List) r1     // Catch: java.lang.Throwable -> L16
            boolean r2 = r1.isEmpty()     // Catch: java.lang.Throwable -> L16
            if (r2 == 0) goto L43
            goto L58
        L43:
            r2 = 0
            java.lang.Object r3 = r1.get(r2)     // Catch: java.lang.Throwable -> L16
            goto L58
        L49:
            java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Throwable -> L16
            boolean r2 = r1.hasNext()     // Catch: java.lang.Throwable -> L16
            if (r2 != 0) goto L54
            goto L58
        L54:
            java.lang.Object r3 = r1.next()     // Catch: java.lang.Throwable -> L16
        L58:
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3     // Catch: java.lang.Throwable -> L16
            if (r3 != 0) goto L5e
            monitor-exit(r0)
            return
        L5e:
            java.lang.Object r1 = r3.getKey()     // Catch: java.lang.Throwable -> L16
            java.lang.Object r2 = r3.getValue()     // Catch: java.lang.Throwable -> L16
            wb0 r3 = r4.b     // Catch: java.lang.Throwable -> L16
            r3.getClass()     // Catch: java.lang.Throwable -> L16
            r1.getClass()     // Catch: java.lang.Throwable -> L16
            java.util.LinkedHashMap r3 = r3.e     // Catch: java.lang.Throwable -> L16
            r3.remove(r1)     // Catch: java.lang.Throwable -> L16
            int r1 = r4.d     // Catch: java.lang.Throwable -> L16
            r2.getClass()     // Catch: java.lang.Throwable -> L16
            int r1 = r1 + (-1)
            r4.d = r1     // Catch: java.lang.Throwable -> L16
            monitor-exit(r0)
            goto L0
        L7e:
            monitor-exit(r0)
            return
        L80:
            java.lang.String r4 = "LruCache.sizeOf() is reporting inconsistent results!"
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L16
            r5.<init>(r4)     // Catch: java.lang.Throwable -> L16
            throw r5     // Catch: java.lang.Throwable -> L16
        L88:
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.vb0.d(int):void");
    }

    public final String toString() {
        int i;
        String str;
        synchronized (this.c) {
            try {
                int i2 = this.e;
                int i3 = this.f + i2;
                if (i3 != 0) {
                    i = (i2 * 100) / i3;
                } else {
                    i = 0;
                }
                str = "LruCache[maxSize=" + this.a + ",hits=" + this.e + ",misses=" + this.f + ",hitRate=" + i + "%]";
            } catch (Throwable th) {
                throw th;
            }
        }
        return str;
    }
}

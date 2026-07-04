package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class dz extends sz0 implements gv {
    public gv i;
    public int j;
    public final /* synthetic */ gv k;
    public final /* synthetic */ gl l;
    public final /* synthetic */ String m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dz(gv gvVar, gl glVar, String str, ij ijVar) {
        super(1, ijVar);
        this.k = gvVar;
        this.l = glVar;
        this.m = str;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        gl glVar = this.l;
        String str = this.m;
        return new dz(this.k, glVar, str, (ij) obj).k(x31.a);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00a5 A[Catch: all -> 0x00c3, LOOP:0: B:21:0x00a3->B:22:0x00a5, LOOP_END, TryCatch #2 {all -> 0x00c3, blocks: (B:20:0x008e, B:22:0x00a5, B:24:0x00ae), top: B:19:0x008e }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00bc A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00bd  */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r5) {
        /*
            r4 = this;
            java.lang.String r0 = "Current AssetManager is null."
            int r1 = r4.j
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L17
            if (r1 != r3) goto L11
            gv r4 = r4.i
            defpackage.o30.x(r5)
            goto Lbe
        L11:
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r4)
            return r2
        L17:
            defpackage.o30.x(r5)
            java.lang.String r5 = r4.m
            gv r1 = r4.k
            r4.i = r1
            r4.j = r3
            gl r4 = r4.l
            r4.getClass()
            android.content.Context r4 = org.jetbrains.compose.resources.AndroidContextProvider.e     // Catch: java.io.FileNotFoundException -> L40
            if (r4 == 0) goto L30
            android.content.res.AssetManager r4 = r4.getAssets()     // Catch: java.io.FileNotFoundException -> L40
            goto L31
        L30:
            r4 = r2
        L31:
            if (r4 == 0) goto L3a
            java.io.InputStream r4 = r4.open(r5)     // Catch: java.io.FileNotFoundException -> L40
            if (r4 == 0) goto L3a
            goto L8e
        L3a:
            java.io.FileNotFoundException r4 = new java.io.FileNotFoundException     // Catch: java.io.FileNotFoundException -> L40
            r4.<init>(r0)     // Catch: java.io.FileNotFoundException -> L40
            throw r4     // Catch: java.io.FileNotFoundException -> L40
        L40:
            android.content.Context r4 = defpackage.x4.b()     // Catch: java.lang.NoClassDefFoundError -> L49 java.io.FileNotFoundException -> L60
            android.content.res.AssetManager r4 = r4.getAssets()     // Catch: java.lang.NoClassDefFoundError -> L49 java.io.FileNotFoundException -> L60
            goto L51
        L49:
            java.lang.String r4 = "ResourceReader"
            java.lang.String r3 = "Android Instrumentation context is not available."
            android.util.Log.d(r4, r3)     // Catch: java.io.FileNotFoundException -> L60
            r4 = r2
        L51:
            if (r4 == 0) goto L5a
            java.io.InputStream r4 = r4.open(r5)     // Catch: java.io.FileNotFoundException -> L60
            if (r4 == 0) goto L5a
            goto L8e
        L5a:
            java.io.FileNotFoundException r4 = new java.io.FileNotFoundException     // Catch: java.io.FileNotFoundException -> L60
            r4.<init>(r0)     // Catch: java.io.FileNotFoundException -> L60
            throw r4     // Catch: java.io.FileNotFoundException -> L60
        L60:
            java.lang.Class<gl> r4 = defpackage.gl.class
            java.lang.ClassLoader r4 = r4.getClassLoader()
            if (r4 == 0) goto Lcf
            java.io.InputStream r4 = r4.getResourceAsStream(r5)
            if (r4 != 0) goto L8e
            android.content.Context r4 = org.jetbrains.compose.resources.AndroidContextProvider.e
            java.lang.String r0 = "Missing resource with path: "
            if (r4 != 0) goto L84
            yc0 r4 = new yc0
            java.lang.String r1 = ". Android context is not initialized. If it happens in the Preview mode then call PreviewContextConfigurationEffect() function."
            java.lang.String r5 = r5.concat(r1)
            java.lang.String r5 = r0.concat(r5)
            r4.<init>(r5)
            throw r4
        L84:
            yc0 r4 = new yc0
            java.lang.String r5 = r0.concat(r5)
            r4.<init>(r5)
            throw r4
        L8e:
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> Lc3
            int r0 = r4.available()     // Catch: java.lang.Throwable -> Lc3
            r2 = 8192(0x2000, float:1.148E-41)
            int r0 = java.lang.Math.max(r2, r0)     // Catch: java.lang.Throwable -> Lc3
            r5.<init>(r0)     // Catch: java.lang.Throwable -> Lc3
            byte[] r0 = new byte[r2]     // Catch: java.lang.Throwable -> Lc3
            int r2 = r4.read(r0)     // Catch: java.lang.Throwable -> Lc3
        La3:
            if (r2 < 0) goto Lae
            r3 = 0
            r5.write(r0, r3, r2)     // Catch: java.lang.Throwable -> Lc3
            int r2 = r4.read(r0)     // Catch: java.lang.Throwable -> Lc3
            goto La3
        Lae:
            byte[] r5 = r5.toByteArray()     // Catch: java.lang.Throwable -> Lc3
            r5.getClass()     // Catch: java.lang.Throwable -> Lc3
            r4.close()
            ik r4 = defpackage.ik.e
            if (r5 != r4) goto Lbd
            return r4
        Lbd:
            r4 = r1
        Lbe:
            java.lang.Object r4 = r4.e(r5)
            return r4
        Lc3:
            r5 = move-exception
            throw r5     // Catch: java.lang.Throwable -> Lc5
        Lc5:
            r0 = move-exception
            r4.close()     // Catch: java.lang.Throwable -> Lca
            goto Lce
        Lca:
            r4 = move-exception
            defpackage.o20.d(r5, r4)
        Lce:
            throw r0
        Lcf:
            java.lang.String r4 = "Cannot find class loader"
            defpackage.v7.o(r4)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dz.k(java.lang.Object):java.lang.Object");
    }
}

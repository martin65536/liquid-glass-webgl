package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class h31 extends y20 {
    /* JADX WARN: Removed duplicated region for block: B:31:0x006c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // defpackage.y20
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.graphics.Typeface f(android.content.Context r3, defpackage.qu[] r4) {
        /*
            r2 = this;
            int r2 = r4.length
            r0 = 1
            r1 = 0
            if (r2 >= r0) goto L7
            goto La1
        L7:
            qu r2 = defpackage.y20.h(r4)
            android.content.ContentResolver r4 = r3.getContentResolver()
            android.net.Uri r2 = r2.a     // Catch: java.io.IOException -> La1
            java.lang.String r0 = "r"
            android.os.ParcelFileDescriptor r2 = r4.openFileDescriptor(r2, r0, r1)     // Catch: java.io.IOException -> La1
            if (r2 != 0) goto L1f
            if (r2 == 0) goto La1
            r2.close()     // Catch: java.io.IOException -> La1
            return r1
        L1f:
            java.lang.String r4 = "/proc/self/fd/"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: android.system.ErrnoException -> L47 java.lang.Throwable -> L59
            r0.<init>(r4)     // Catch: android.system.ErrnoException -> L47 java.lang.Throwable -> L59
            int r4 = r2.getFd()     // Catch: android.system.ErrnoException -> L47 java.lang.Throwable -> L59
            r0.append(r4)     // Catch: android.system.ErrnoException -> L47 java.lang.Throwable -> L59
            java.lang.String r4 = r0.toString()     // Catch: android.system.ErrnoException -> L47 java.lang.Throwable -> L59
            java.lang.String r4 = android.system.Os.readlink(r4)     // Catch: android.system.ErrnoException -> L47 java.lang.Throwable -> L59
            android.system.StructStat r0 = android.system.Os.stat(r4)     // Catch: android.system.ErrnoException -> L47 java.lang.Throwable -> L59
            int r0 = r0.st_mode     // Catch: android.system.ErrnoException -> L47 java.lang.Throwable -> L59
            boolean r0 = android.system.OsConstants.S_ISREG(r0)     // Catch: android.system.ErrnoException -> L47 java.lang.Throwable -> L59
            if (r0 == 0) goto L47
            java.io.File r0 = new java.io.File     // Catch: android.system.ErrnoException -> L47 java.lang.Throwable -> L59
            r0.<init>(r4)     // Catch: android.system.ErrnoException -> L47 java.lang.Throwable -> L59
            goto L48
        L47:
            r0 = r1
        L48:
            if (r0 == 0) goto L5b
            boolean r4 = r0.canRead()     // Catch: java.lang.Throwable -> L59
            if (r4 != 0) goto L51
            goto L5b
        L51:
            android.graphics.Typeface r3 = android.graphics.Typeface.createFromFile(r0)     // Catch: java.lang.Throwable -> L59
            r2.close()     // Catch: java.io.IOException -> La1
            return r3
        L59:
            r3 = move-exception
            goto L98
        L5b:
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L59
            java.io.FileDescriptor r0 = r2.getFileDescriptor()     // Catch: java.lang.Throwable -> L59
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L59
            java.io.File r3 = defpackage.g30.t(r3)     // Catch: java.lang.Throwable -> L8e
            if (r3 != 0) goto L6c
        L6a:
            r0 = r1
            goto L87
        L6c:
            boolean r0 = defpackage.g30.o(r3, r4)     // Catch: java.lang.RuntimeException -> L72 java.lang.Throwable -> L82
            if (r0 != 0) goto L76
        L72:
            r3.delete()     // Catch: java.lang.Throwable -> L8e
            goto L6a
        L76:
            java.lang.String r0 = r3.getPath()     // Catch: java.lang.RuntimeException -> L72 java.lang.Throwable -> L82
            android.graphics.Typeface r0 = android.graphics.Typeface.createFromFile(r0)     // Catch: java.lang.RuntimeException -> L72 java.lang.Throwable -> L82
            r3.delete()     // Catch: java.lang.Throwable -> L8e
            goto L87
        L82:
            r0 = move-exception
            r3.delete()     // Catch: java.lang.Throwable -> L8e
            throw r0     // Catch: java.lang.Throwable -> L8e
        L87:
            r4.close()     // Catch: java.lang.Throwable -> L59
            r2.close()     // Catch: java.io.IOException -> La1
            return r0
        L8e:
            r3 = move-exception
            r4.close()     // Catch: java.lang.Throwable -> L93
            goto L97
        L93:
            r4 = move-exception
            r3.addSuppressed(r4)     // Catch: java.lang.Throwable -> L59
        L97:
            throw r3     // Catch: java.lang.Throwable -> L59
        L98:
            r2.close()     // Catch: java.lang.Throwable -> L9c
            goto La0
        L9c:
            r2 = move-exception
            r3.addSuppressed(r2)     // Catch: java.io.IOException -> La1
        La0:
            throw r3     // Catch: java.io.IOException -> La1
        La1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.h31.f(android.content.Context, qu[]):android.graphics.Typeface");
    }
}

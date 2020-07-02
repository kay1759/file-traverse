(ns file-traverse.core)

(declare traverse ltraverse execute)

(defn traverse
  "execute func for all files in pathname, including files in sub directories"
  ([pathname func]
   (traverse pathname func (fn [x] true)))

  ([pathname func filter_file]
   (traverse pathname func filter_file (fn [x] true)))

  ([pathname func filter_file filter_dir]
   (let [path (java.io.File. pathname)]
     (execute path func filter_file filter_dir))))

(defmulti execute
  "if path is a file, exec (func path), if path is a directory recursive call for path"
  (fn [path func filter_file filter_dir]
    (if (.isFile path) :file :directory)))

(defmethod execute
  :file
  ;;; exec (func path)
  [path func filter_file filter_dir]
  (when (filter_file path)
    (func path)))

(defmethod execute
  :directory
  ;;; recursive call for path
  [path func filter_file filter_dir]
  (when (filter_dir path)
    (doseq [p (.listFiles path)]
      (execute p func filter_file filter_dir))))

(defn ltraverse
  "execute func for all files in pathname, not including files in sub directories"
  ([pathname func]
   (ltraverse pathname func (fn [x] true)))

  ([pathname func filter_file]
   (let [path (java.io.File. pathname)]
     (if (.isFile path)
       (when (filter_file path)
         (func path))
       (doseq [path1 (.listFiles path)]
         (if (.isFile path1)
           (when (filter_file path1)
             (func path1))))))))

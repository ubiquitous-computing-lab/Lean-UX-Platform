'use strict'

let log = console.log.bind(console),
  id = val => document.getElementById(val),
  ul = id('ul'),
  gUMbtn = id('gUMbtn'),
  start = id('start'),
  stop = id('stop'),
  stream,
  recorder,
  counter=1,
  chunks,
  media;


let mv = id('mediaVideo'),
      mediaOptions = {
        audio: {
          tag: 'audio',
          type: 'audio/wav',
          ext: '.wav',
          gUM: {audio: true}
        }
      };
   media =  mediaOptions.audio;
  navigator.mediaDevices.getUserMedia(media.gUM).then(_stream => {
    stream = _stream;
    recorder = new MediaRecorder(stream);
    recorder.ondataavailable = e => {
      chunks.push(e.data);
      if(recorder.state == 'inactive')  makeLink();
    };
    log('got media successfully');
  }).catch(log);

start.onclick = e => {
  start.disabled = true;
  stop.removeAttribute('disabled');
  chunks=[];
  recorder.start();
}


stop.onclick = e => {
  stop.disabled = true;
  recorder.stop();
  start.removeAttribute('disabled');
}


function makeLink(){
  console.log('chunks');
  console.log(chunks);
  ///let blob = new Blob(chunks, 'test.wav', {type: media.type });
  
  var file = new File([chunks], 'test.wav');





  /*if (window.navigator.msSaveOrOpenBlob) // IE10+
        window.navigator.msSaveOrOpenBlob(blob, 'test.wav');
    else { // Others
        var a = document.createElement("a"),
                url = URL.createObjectURL(blob);
        a.href = url;
        a.download = 'test.wav';
        document.body.appendChild(a);
        a.click();
        setTimeout(function() {
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);  
        }, 0); 
    }*/









    
  mt.controls = true;

  console.log(`donwload ${hf.download}`)
  mt.src = url;
  hf.href = url;
  hf.download = `${counter++}${media.ext}`;
  var audiofile = `donwload ${hf.download}`;
  hf.innerHTML = `donwload ${hf.download}`;
/*  downloadFile(chunks);*/
  //downloadFile('testing test');
  li.appendChild(mt);
  li.appendChild(hf);
  ul.appendChild(li);
  console.log(hf.href)
}
